package com.wipro.promotion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.promotion.entity.MapPromotionProduct;
import com.wipro.promotion.entity.Promotion;
import com.wipro.promotion.repository.MapPromotionProductRepository;
import com.wipro.promotion.repository.PromotionRepository;

@Service
public class PromotionServiceImpl implements PromotionService{
	@Autowired
	private PromotionRepository promotionRepo;
	
	@Autowired
	private MapPromotionProductRepository promotionProductRepo;

	@Override
	public Promotion addPromotion(Promotion promotion) {
		return promotionRepo.save(promotion);
	}

	@Override
	public Promotion updatePromotion(Promotion promotion) {
		Optional<Promotion> optPromo=promotionRepo.findById(promotion.getPromotionID());
		if(optPromo.isPresent()) {
			Promotion promo=optPromo.get();
			promotion.setPromotionID(promo.getPromotionID());
			return promotionRepo.save(promotion);
		}else
			return null;
	}
	
	public List<Promotion> viewAllPromotion(){
		return promotionRepo.findAll();
	}

	@Override
	public Promotion findPromotionById(int promotionID) {
		Optional<Promotion> foundPromotion=promotionRepo.findById(promotionID);
		if(foundPromotion.isPresent()) {
			return foundPromotion.get();
		}else
			return null;
	}

	@Override
	public Promotion findPromotionByCode(String promotionCode) {
		return promotionRepo.findByPromotionCode(promotionCode);
	}

	@Override
	public void deletePromotion(int promotionID) {
		promotionRepo.deleteById(promotionID);
	}

	@Override
	public MapPromotionProduct mapProductToPromotion(long productId, int promotionID) {
		MapPromotionProduct promotionProduct=new MapPromotionProduct();
		promotionProduct.setProductId(productId);
		promotionProduct.setPromotionID(promotionID);
		return promotionProductRepo.save(promotionProduct);
	}

	@Override
	public List<Promotion> findPromotionForProduct(long productId) {
		List<MapPromotionProduct> promotionProduct=promotionProductRepo.findAllPromotionByProductId(productId);
		List<Promotion> promoList=new ArrayList<>();
		for(MapPromotionProduct p:promotionProduct) {
			Optional<Promotion> promotionOptional=promotionRepo.findById(p.getPromotionID());
			if(promotionOptional.isPresent()) {
				promoList.add(promotionOptional.get());
			}
		}
		return promoList;
	}
	

}
