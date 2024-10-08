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

    // Tạo ID mới cho ShopCart với định dạng SC001, SC002, ...
    private String generateNextShopCartId() {
        ShopCart lastShopCart = shopCartRepository.findTopByOrderByShopCartIDDesc();
        if (lastShopCart != null) {
            String lastId = lastShopCart.getShopCartID();
            int idNumber = Integer.parseInt(lastId.substring(2));  // Bỏ 'SC' và chuyển phần còn lại thành số
            String nextId = String.format("SC%03d", idNumber + 1);  // Tạo ID mới theo dạng SCxxx
            return nextId;
        } else {
            return "SC001";  // ID đầu tiên
        }
    }

    // Thêm sản phẩm hoặc combo vào giỏ hàng
    public ShopCartResponse addToCart(ShopCartRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + request.getAccountId() + " not found"));

        ShopCart shopCart = new ShopCart();
        shopCart.setShopCartID(generateNextShopCartId());
        shopCart.setAccount(account);  // Gán account

        // Tìm trong bảng Product trước
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
        Product product = null;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            shopCart.setBreed(product.getBreed());
            shopCart.setPrice(product.getPrice());
            shopCart.setQuantity(product.getQuantity());
            shopCart.setType(product.getType());
            shopCart.setProduct(product);
        } else {
            // Nếu không có trong Product, kiểm tra ProductCombo
            ProductCombo productCombo = productComboRepository.findById(request.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product or Combo with ID " + request.getProductId() + " not found"));
            shopCart.setBreed(productCombo.getBreed());
            shopCart.setPrice(productCombo.getPrice());
            shopCart.setQuantity(productCombo.getQuantity());
            shopCart.setType(productCombo.getType());
            shopCart.setProductCombo(productCombo);
        }

        // Lưu vào database
        ShopCart savedCart = shopCartRepository.save(shopCart);

        // Trả về ShopCartResponse
        return modelMapper.map(savedCart, ShopCartResponse.class);
    }

    // Lấy tất cả sản phẩm trong giỏ hàng của một tài khoản
    public List<ShopCartResponse> getCartItems(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " +accountId + " not found"));

        return shopCartRepository.findByAccount_AccountID(accountId).stream()
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
