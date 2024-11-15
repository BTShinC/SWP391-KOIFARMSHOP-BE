package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.ShopCartRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ShopCartResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ShopCart;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductComboRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IShopCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopCartService {

    @Autowired
    private IShopCartRepository shopCartRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IProductComboRepository productComboRepository;

    @Autowired
    private ModelMapper modelMapper;

    private String generateNextShopCartId() {
        ShopCart lastShopCart = shopCartRepository.findTopByOrderByShopCartIDDesc();
        if (lastShopCart != null) {
            String lastId = lastShopCart.getShopCartID();
            int idNumber = Integer.parseInt(lastId.substring(2));
            String nextId = String.format("SC%03d", idNumber + 1);
            return nextId;
        } else {
            return "SC001";
        }
    }

    // Thêm sản phẩm hoặc combo vào giỏ hàng
    public ShopCartResponse addToCart(ShopCartRequest request) {
        // Kiểm tra xem Account có tồn tại không
        Account account = accountRepository.findById(request.getAccountID())
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + request.getAccountID() + " not found"));

        // Kiểm tra xem sản phẩm hoặc combo đã tồn tại trong giỏ hàng chưa
        List<ShopCart> existingCartItems = shopCartRepository.findByAccount_AccountID(request.getAccountID());

        // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<ShopCart> existingProductCart = existingCartItems.stream()
                .filter(cartItem -> cartItem.getProduct() != null
                        && cartItem.getProduct().getProductID().equals(request.getAccountID()))
                .findFirst();

        if (existingProductCart.isPresent()) {
            throw new IllegalArgumentException("Product with ID " + request.getAccountID() + " is already in the cart.");
        }

        // Kiểm tra combo sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<ShopCart> existingProductComboCart = existingCartItems.stream()
                .filter(cartItem -> cartItem.getProductCombo() != null
                        && cartItem.getProductCombo().getProductComboID().equals(request.getProductID()))
                .findFirst();

        if (existingProductComboCart.isPresent()) {
            throw new IllegalArgumentException("Product combo with ID " + request.getProductID() + " is already in the cart.");
        }

        // Tạo ShopCart mới
        ShopCart shopCart = new ShopCart();
        shopCart.setShopCartID(generateNextShopCartId());
        shopCart.setAccount(account);  // Gán account

        // Tìm trong bảng Product trước
        Optional<Product> optionalProduct = productRepository.findById(request.getProductID());
        ShopCartResponse response = new ShopCartResponse();

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Kiểm tra trạng thái của Product
            if (!"Còn hàng".equals(product.getStatus())) {
                throw new IllegalArgumentException("Product with ID " + request.getProductID() + " is not available for sale.");
            }

            shopCart.setBreed(product.getBreed());
            shopCart.setPrice(product.getPrice());
            shopCart.setQuantity(product.getQuantity());
            shopCart.setType("Product");
            shopCart.setName(product.getProductName());
            shopCart.setImage(product.getImage());
            shopCart.setProduct(product);
            response.setProductID(product.getProductID());

        } else {
            // Nếu không có trong Product, kiểm tra ProductCombo
            ProductCombo productCombo = productComboRepository.findById(request.getProductID())
                    .orElseThrow(() -> new EntityNotFoundException("Product or Combo with ID " + request.getProductID() + " not found"));

            // Kiểm tra trạng thái của ProductCombo
            if (!"Còn hàng".equals(productCombo.getStatus())) {
                throw new IllegalArgumentException("Product combo with ID " + request.getProductID() + " is not available for sale.");
            }

            shopCart.setBreed(productCombo.getBreed());
            shopCart.setPrice(productCombo.getPrice());
            shopCart.setQuantity(productCombo.getQuantity());
            shopCart.setType("ProductCombo");
            shopCart.setName(productCombo.getComboName());
            shopCart.setImage(productCombo.getImage());
            shopCart.setProductCombo(productCombo);
            response.setProductComboID(productCombo.getProductComboID());
        }

        // Lưu vào database
        ShopCart savedCart = shopCartRepository.save(shopCart);

        // Gán giá trị vào response
        response.setShopCartID(savedCart.getShopCartID());
        response.setBreed(savedCart.getBreed());
        response.setPrice(savedCart.getPrice());
        response.setQuantity(savedCart.getQuantity());
        response.setType(savedCart.getType());
        response.setAccountID(savedCart.getAccount().getAccountID());
        response.setName(savedCart.getName());
        response.setImage(savedCart.getImage());

        return response;
    }

    // Lấy tất cả sản phẩm trong giỏ hàng của một tài khoản
    public List<ShopCartResponse> getCartItems(String accountId) {
        // Tìm tài khoản
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountId + " not found"));

        // Lấy tất cả các mục giỏ hàng cho tài khoản
        List<ShopCart> cartItems = shopCartRepository.findByAccount_AccountID(accountId);

        // Lọc chỉ những sản phẩm và productCombo có trạng thái là "Còn hàng"
        return cartItems.stream()
                .filter(cartItem ->
                        (cartItem.getProduct() != null && "Còn hàng".equals(cartItem.getProduct().getStatus())) ||
                                (cartItem.getProductCombo() != null && "Còn hàng".equals(cartItem.getProductCombo().getStatus()))
                )
                .map(cartItem -> modelMapper.map(cartItem, ShopCartResponse.class))
                .collect(Collectors.toList());
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public ShopCartResponse updateCartItem(String cartItemId, int newQuantity) {
        ShopCart cartItem = shopCartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(newQuantity);
        ShopCart updatedCart = shopCartRepository.save(cartItem);
        return modelMapper.map(updatedCart, ShopCartResponse.class);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeCartItem(String cartItemId) {
        ShopCart cartItem = shopCartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        shopCartRepository.deleteById(cartItemId);
    }

    // Xóa tất cả sản phẩm trong giỏ hàng của một tài khoản
    public void clearCart(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " +accountId + " not found"));

        List<ShopCart> cartItems = shopCartRepository.findByAccount_AccountID(accountId);
        shopCartRepository.deleteAll(cartItems);
    }
}
