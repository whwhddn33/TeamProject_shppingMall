package dto;

public class BasketDTO {
	private int basketIdx; // ��ٱ��� ������ȣ(PK)
	private String userId; // ȸ�����̵�(FK)
	private int prodIdx; // ��ǰ������ȣ(FK)
	private String prodName; // ��ǰ�̸�(FK)
	private int pordprice; // ����
	
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
		return "��ٱ��Ϲ�ȣ : "+basketIdx+" / ��ǰ��ȣ : "+prodIdx+" / ��ǰ�� : "+prodName+" / ���� : "+pordprice;
	}
}
