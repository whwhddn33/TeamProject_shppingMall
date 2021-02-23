package dto;

public class BasketDTO {
	private int basketIdx; // 장바구니 고유번호(PK)
	private String userId; // 회원아이디(FK)
	private int prodIdx; // 상품고유번호(FK)
	private String prodName; // 상품이름(FK)
	private int pordprice; // 가격
	
	public BasketDTO() {}
	
	public BasketDTO(int basketIdx, String userId, int prodIdx, String prodName, int pordprice) {
		this.basketIdx = basketIdx;
		this.userId = userId;
		this.prodIdx = prodIdx;
		this.prodName = prodName;
		this.pordprice = pordprice;
	}
	
	public int getBasketIdx() {
		return basketIdx;
	}
	public void setBasketIdx(int basketIdx) {
		this.basketIdx = basketIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getProdIdx() {
		return prodIdx;
	}
	public void setProdIdx(int prodIdx) {
		this.prodIdx = prodIdx;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getPordprice() {
		return pordprice;
	}
	public void setPordprice(int pordprice) {
		this.pordprice = pordprice;
	}

	@Override
	public String toString() {
		return "장바구니번호 : "+basketIdx+" / 상품번호 : "+prodIdx+" / 상품명 : "+prodName+" / 가격 : "+pordprice;
	}
}