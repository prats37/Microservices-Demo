package com.wipro.promotion.service;

import java.util.List;

import com.wipro.promotion.entity.MapPromotionProduct;
import com.wipro.promotion.entity.Promotion;

public interface PromotionService {

	Promotion addPromotion(Promotion promotion);

	Promotion updatePromotion(Promotion promotion);

	List<Promotion> viewAllPromotion();

	Promotion findPromotionById(int promotionID);

	Promotion findPromotionByCode(String promotionCode);

	void deletePromotion(int promotionID);

	MapPromotionProduct mapProductToPromotion(long productId, int promotionID);

	List<Promotion> findPromotionForProduct(long productId);

}
