package com.nisum.vibe.cart.scm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import java.math.BigDecimal;

/**
 * Entity class representing an item within an order.
 * <p>
 * This class maps to the "vibe_cart_order_items" table in the database. Each instance of this class corresponds to
 * a specific item included in an order. It contains details such as the item ID, SKU ID, item name, category, selected
 * size and color, quantity, and pricing information. This class is associated with an {@link Order} entity through a
 * many-to-one relationship.
 * </p>
 */
@Entity
@Table(name = "vibe_cart_order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "category")
    private String category;

    @Column(name = "size")
    private String selectedSize;

    @Column(name = "colour")
    private String selectedColor;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal price;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    /**
     * Constructs an {@code OrderItem} with the specified details.
     *
     * @param orderItemId   the unique identifier for the order item
     * @param order         the associated order
     * @param itemId        the ID of the item
     * @param skuId         the SKU ID of the item
     * @param itemName      the name of the item
     * @param category      the category of the item
     * @param selectedSize  the selected size of the item
     * @param selectedColor the selected color of the item
     * @param quantity      the quantity of the item
     * @param price         the unit price of the item
     * @param totalPrice    the total price for the quantity of the item
     */
    public OrderItem(Long orderItemId, Order order, Long itemId, Long skuId, String itemName, String category, String selectedSize, String selectedColor, int quantity, BigDecimal price, BigDecimal totalPrice) {
        this.orderItemId = orderItemId;
        this.order = order;
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
     * Default constructor for {@code OrderItem}.
     */
    public OrderItem() {
    }

    /**
     * Gets the unique identifier for the order item.
     *
     * @return the unique identifier for the order item
     */
    public Long getOrderItemId() {
        return orderItemId;
    }

    /**
     * Sets the unique identifier for the order item.
     *
     * @param orderItemId the unique identifier for the order item
     */
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * Gets the associated order for this item.
     *
     * @return the associated order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the associated order for this item.
     *
     * @param order the associated order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Gets the ID of the item.
     *
     * @return the ID of the item
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * Sets the ID of the item.
     *
     * @param itemId the ID of the item
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets the SKU ID of the item.
     *
     * @return the SKU ID of the item
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * Sets the SKU ID of the item.
     *
     * @param skuId the SKU ID of the item
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * Gets the name of the item.
     *
     * @return the name of the item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     *
     * @param itemName the name of the item
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the category of the item.
     *
     * @return the category of the item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the item.
     *
     * @param category the category of the item
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the selected size of the item.
     *
     * @return the selected size of the item
     */
    public String getSelectedSize() {
        return selectedSize;
    }

    /**
     * Sets the selected size of the item.
     *
     * @param selectedSize the selected size of the item
     */
    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    /**
     * Gets the selected color of the item.
     *
     * @return the selected color of the item
     */
    public String getSelectedColor() {
        return selectedColor;
    }

    /**
     * Sets the selected color of the item.
     *
     * @param selectedColor the selected color of the item
     */
    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * Gets the quantity of the item.
     *
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity the quantity of the item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the unit price of the item.
     *
     * @return the unit price of the item
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the unit price of the item.
     *
     * @param price the unit price of the item
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the total price for the quantity of the item.
     *
     * @return the total price for the quantity of the item
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price for the quantity of the item.
     *
     * @param totalPrice the total price for the quantity of the item
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
