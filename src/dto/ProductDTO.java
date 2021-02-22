package dto;

public class ProductDTO {
	private int prodIdx; // 상품 고유 번호(PK)
	private String prodDate; // 상품등록일자
	private String prodName; // 이름
	private String prodDes; // 설명
	private int prodPrice; // 가격
	private int prodLike; // 좋아요수
	private int prodAmount; // 재고수량
	private String prodState; // 구매 가능 상태

	public ProductDTO() {
		;
	}

	public ProductDTO(String prodDate, String prodName, String prodDes, int prodPrice, int prodAmount,
			String prodState) {
		this.prodDate = prodDate;
		this.prodName = prodName;
		this.prodDes = prodDes;
		this.prodPrice = prodPrice;
		this.prodAmount = prodAmount;
		this.prodState = prodState;
		this.prodIdx = 0;
		this.prodLike = 0;

	}

	public int getProdIdx() {
		return prodIdx;
	}

	public void setProdIdx(int prodIdx) {
		this.prodIdx = prodIdx;
	}

	public String getProdDate() {
		return prodDate;
	}

	public void setProdDate(String prodDate) {
		this.prodDate = prodDate;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDes() {
		return prodDes;
	}

	public void setProdDes(String prodDes) {
		this.prodDes = prodDes;
	}

	public int getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(int prodPrice) {
		this.prodPrice = prodPrice;
	}

	public int getProdLike() {
		return prodLike;
	}

	public void setProdLike(int prodLike) {
		this.prodLike = prodLike;
	}

	public int getProdAmount() {
		return prodAmount;
	}

	public void setProdAmount(int prodAmount) {
		this.prodAmount = prodAmount;
	}

	public String getProdStae() {
		return prodState;
	}

	public void setProdStae(String prodStae) {
		this.prodState = prodStae;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProductDTO) {
			ProductDTO target = (ProductDTO) obj;
			if (target.prodIdx == this.prodIdx) {
				return true;
			}
		}
		return false;
	}
}
