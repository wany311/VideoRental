import java.util.Date;

public class Video {
	private String title;
	private VideoType videoType;
	private PriceCode priceCode;
	private Date registeredDate;
	private boolean rented;

	public Video(String title, VideoType videoType, PriceCode priceCode, Date registeredDate) {
		this.title = title;
		this.videoType = videoType;
		this.priceCode = priceCode;
		this.registeredDate = registeredDate;
	}

	public int getLateReturnPointPenalty() {
		switch (videoType) {
			case VHS:
				return 1;
			case CD:
				return 2;
			case DVD:
				return 3;
			default:
				return 0; // 알 수 없는 유형의 경우
		}
	}

	public PriceCode getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(PriceCode priceCode) {
		this.priceCode = priceCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public VideoType getVideoType() {
		return videoType;
	}

	public void setVideoType(VideoType videoType) {
		this.videoType = videoType;
	}
}
