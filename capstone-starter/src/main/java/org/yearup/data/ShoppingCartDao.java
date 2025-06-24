package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCart create(int userId, int productId);
    public void update(int userId, int productId, int quantity);
    public void delete(int userId);
}
