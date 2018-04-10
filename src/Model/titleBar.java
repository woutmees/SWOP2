package Model;

public class titleBar {
	private String title;
	private Button button;
	
	public titleBar() {
		this.button = new Button();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
	
}
