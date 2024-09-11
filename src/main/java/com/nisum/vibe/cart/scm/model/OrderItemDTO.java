package com.nisum.vibe.cart.scm.model;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for representing details of an item in an order.
 * <p>
 * Contains fields for order item ID, item ID, SKU ID, item name, category, selected size, selected color,
 * quantity, price, and total price.
 * </p>
 */
public class OrderItemDTO {

    private Long orderItemId;
    private Long itemId;
    private Long skuId;
    private String itemName;
    private String category;
    private String selectedSize;
    private String selectedColor;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    /**
     * Default constructor.
     */
    public OrderItemDTO() {
    }

    /**
     * Constructs an OrderItemDTO with the specified parameters.
     *
     * @param orderItemId the unique identifier for the order item
     * @param itemId the unique identifier for the item
     * @param skuId the unique identifier for the SKU
     * @param itemName the name of the item
     * @param category the category of the item
     * @param selectedSize the size selected for the item
     * @param selectedColor the color selected for the item
     * @param quantity the quantity of the item
     * @param price the price of the item
     * @param totalPrice the total price of the item (quantity * price)
     */
    public OrderItemDTO(Long orderItemId, Long itemId, Long skuId, String itemName, String category, String selectedSize, String selectedColor, int quantity, BigDecimal price, BigDecimal totalPrice) {
        this.orderItemId = orderItemId;
        this.itemId = itemId;
        this.skuId = skuId;
        this.itemName = itemName;
        this.category = category;
        this.selectedSize = selectedSize;
        this.selectedColor = selectedColor;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    /**
     * Returns the unique identifier for the order item.
     *
     * @return the order item ID
     */
    public Long getOrderItemId() {
        return orderItemId;
    }

    /**
     * Sets the unique identifier for the order item.
     *
     * @param orderItemId the order item ID
     */
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * Returns the unique identifier for the item.
     *
     * @return the item ID
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * Sets the unique identifier for the item.
     *
     * @param itemId the item ID
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the unique identifier for the SKU.
     *
     * @return the SKU ID
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * Sets the unique identifier for the SKU.
     *
     * @param skuId the SKU ID
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * Returns the name of the item.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     *
     * @param itemName the item name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the category of the item.
     *
     * @return the item category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the item.
     *
     * @param category the item category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the size selected for the item.
     *
     * @return the selected size
     */
    public String getSelectedSize() {
        return selectedSize;
    }

    /**
     * Sets the size selected for the item.
     *
     * @param selectedSize the selected size
     */
    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    /**
     * Returns the color selected for the item.
     *
     * @return the selected color
     */
    public String getSelectedColor() {
        return selectedColor;
    }

    /**
     * Sets the color selected for the item.
     *
     * @param selectedColor the selected color
     */
    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * Returns the quantity of the item.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the price of the item.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the item.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns the total price of the item.
     * <p>
     * This is calculated as the quantity multiplied by the price.
     * </p>
     *
     * @return the total price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the item.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
