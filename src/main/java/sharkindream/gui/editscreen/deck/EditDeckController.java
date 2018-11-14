package sharkindream.gui.editscreen.deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sharkindream.actioncard.ActionCard;
import sharkindream.config.Setting;
import sharkindream.deck.Deck;
import sharkindream.gamecharacter.Type;
import sharkindream.gui.editscreen.deck.resource.SubmanuResourceController;
import sharkindream.gui.editscreen.deck.type.SubmanuTypeController;

public class EditDeckController {

	private Deck deck;
	private int deckid = 0;

	@FXML
	public AnchorPane submanu;

	@FXML
	public AnchorPane card0;
	@FXML
	public AnchorPane card1;
	@FXML
	public AnchorPane card2;
	@FXML
	public AnchorPane card3;

	@FXML
	public Text typetext;
	@FXML
	public Text resourcetext;
	@FXML
	public Text powertext;
	@FXML
	public Text bufftext;
	@FXML
	public Text targettext;
	@FXML
	public Text accuracylucktext;
	@FXML
	public Text costtext;



	private boolean isclosesubmanu = true;
	private final int SUBMANUWIDTH = 150;
	private final int SUBHEIGHT = 200;

	Deck[] decklist = new Deck[Setting.MaxDecknum];

	public enum Settinglist{
		Type,
		Resource,
		Power,
		Buff,
		Target,
		Accuracy_Luck
	}



	/*
	//初期化、デッキデータの読み込み
	public void initDeck() {
		for(int id=0; id<Setting.MaxDecknum; ++id) {
			decklist[id] = new Deck().readDeckfromjson(id);
		}
	}
	*/

	public void initpage() {
		deck = (new Deck()).readDeckfromjson(deckid);

		updateUIInfo(deck);
		
	}

	 private void updateUIInfo(Deck deck) {
		 Map<ActionCard, AnchorPane> parecardset = new HashMap<>();
			AnchorPane[] cardlist = {card0, card1, card2, card3};
			for(int i=0; i<4; ++i) {
				parecardset.put(deck.cardlist[i], cardlist[i]);
			}

			for(Map.Entry<ActionCard, AnchorPane> cardentry : parecardset.entrySet()) {

				setCardShowName(cardentry.getValue(), cardentry.getKey().power.getshowname());
				setCardColor(cardentry.getValue(), cardentry.getKey().type.getColor());
				//真ん中のアイコン更新
			}
	 }

	 private void setCardShowName(AnchorPane pane, String showname) {
		 ((Label)((AnchorPane)pane.getChildren().get(0)).getChildren().get(0)).setText(showname);
		 ((Label)((AnchorPane)pane.getChildren().get(1)).getChildren().get(0)).setText(showname);
	 }

	 private void setCardColor(AnchorPane pane, String color) {
		 //TODO: pane追加
		 
		 ((Label)((AnchorPane)pane.getChildren().get(0)).getChildren().get(0)).setTextFill(Paint.valueOf(color));
		 ((Label)((AnchorPane)pane.getChildren().get(1)).getChildren().get(0)).setTextFill(Paint.valueOf(color));
	 }

	@FXML
	public void onMouseClockCard(MouseEvent e) {
		float sscale = (((AnchorPane)e.getSource()).getScaleX() < 0.6) ? 0.5f : 0.6f;
		float escale = (((AnchorPane)e.getSource()).getScaleX() < 0.6) ? 0.6f : 0.5f;

		ScaleTransition scaletransition = new ScaleTransition();
		scaletransition.setNode((Node) e.getSource());
		scaletransition.setFromX(sscale); scaletransition.setFromY(sscale);
		scaletransition.setToX(escale);   scaletransition.setToY(escale);
		scaletransition.setDuration(Duration.seconds(0.2));
		scaletransition.setInterpolator(Interpolator.EASE_BOTH);

		scaletransition.play();

	}

	@FXML
	public void onMouseClickSetting(MouseEvent e) {
				switch( ((Text)((AnchorPane)((Text)e.getSource()).getParent()).getChildren().get(0)).getText() ) {
		case "Type:":
			System.out.println(((Text)((AnchorPane)((Text)e.getSource()).getParent()).getChildren().get(0)).getText());
			opensubmanu(0);
			break;
		case "Resource:":
			System.out.println(((Text)((AnchorPane)((Text)e.getSource()).getParent()).getChildren().get(0)).getText());
			opensubmanu(1);
			break;


		}
	}

	 void opensubmanu(int id) {


		((AnchorPane)submanu.getChildren().get(2)).getChildren().clear();
		Timeline timeline = new Timeline();



		if(isclosesubmanu) {
			//サブメニューを開く

			Line uline = (Line) submanu.getChildren().get(0);
			Line dline = (Line) submanu.getChildren().get(1);

			KeyValue kvu0 = new KeyValue(uline.endXProperty(), 0, Interpolator.LINEAR);
			KeyFrame kfu0 = new KeyFrame(Duration.ZERO, kvu0);

			KeyValue kvu1 = new KeyValue(uline.endXProperty(), this.SUBMANUWIDTH);
			KeyFrame kfu1 = new KeyFrame(Duration.seconds(0.3), kvu1);

			KeyValue kvd0 = new KeyValue(dline.endXProperty(), 0, Interpolator.LINEAR);
			KeyFrame kfd0 = new KeyFrame(Duration.ZERO, kvd0);

			KeyValue kvd1 = new KeyValue(dline.endXProperty(), this.SUBMANUWIDTH);
			KeyFrame kfd1 = new KeyFrame(Duration.seconds(0.3), kvd1);

			KeyValue kvp0 = new KeyValue(submanu.prefHeightProperty(), 0, Interpolator.LINEAR);
			KeyFrame kfp0 = new KeyFrame(Duration.seconds(0.1), kvp0);

			KeyValue kvp1 = new KeyValue(submanu.prefHeightProperty(), this.SUBHEIGHT);
			KeyFrame kfp1 = new KeyFrame(Duration.seconds(0.4), kvp1);


			timeline.getKeyFrames().add(kfu0);
			timeline.getKeyFrames().add(kfu1);

			timeline.getKeyFrames().add(kfd0);
			timeline.getKeyFrames().add(kfd1);

			timeline.getKeyFrames().add(kfp0);
			timeline.getKeyFrames().add(kfp1);

			timeline.play();

			switch(id) {
			case 0:
				try {
					FXMLLoader typefxml = new FXMLLoader(getClass().getResource("/danzaigame/gui/editscreen/deck/type/SubmanuType.fxml"));

					AnchorPane pane = typefxml.load();
					((SubmanuTypeController)typefxml.getController()).init(this);
					((AnchorPane)submanu.getChildren().get(2)).getChildren().add(pane);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					FXMLLoader resourcefxml = new FXMLLoader(getClass().getResource("/danzaigame/gui/editscreen/deck/resource/SubmanuResource.fxml"));

					AnchorPane pane = resourcefxml.load();
					((SubmanuResourceController)resourcefxml.getController()).getController(this);
					((AnchorPane)submanu.getChildren().get(2)).getChildren().add(pane);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				break;
			}


			this.isclosesubmanu = false;

		}else {
			//閉じる
			Line uline = (Line) submanu.getChildren().get(0);
			Line dline = (Line) submanu.getChildren().get(1);

			KeyValue kvu0 = new KeyValue(uline.endXProperty(), 0, Interpolator.LINEAR);
			KeyFrame kfu0 = new KeyFrame(Duration.seconds(0.3), kvu0);

			KeyValue kvu1 = new KeyValue(uline.endXProperty(), this.SUBMANUWIDTH);
			KeyFrame kfu1 = new KeyFrame(Duration.ZERO, kvu1);

			KeyValue kvd0 = new KeyValue(dline.endXProperty(), 0, Interpolator.LINEAR);
			KeyFrame kfd0 = new KeyFrame(Duration.seconds(0.3), kvd0);

			KeyValue kvd1 = new KeyValue(dline.endXProperty(), this.SUBMANUWIDTH);
			KeyFrame kfd1 = new KeyFrame(Duration.ZERO, kvd1);

			KeyValue kvp0 = new KeyValue(submanu.prefHeightProperty(), 0, Interpolator.LINEAR);
			KeyFrame kfp0 = new KeyFrame(Duration.seconds(0.4), kvp0);

			KeyValue kvp1 = new KeyValue(submanu.prefHeightProperty(), this.SUBHEIGHT);
			KeyFrame kfp1 = new KeyFrame(Duration.seconds(0.1), kvp1);


			timeline.getKeyFrames().add(kfu0);
			timeline.getKeyFrames().add(kfu1);

			timeline.getKeyFrames().add(kfd0);
			timeline.getKeyFrames().add(kfd1);

			timeline.getKeyFrames().add(kfp0);
			timeline.getKeyFrames().add(kfp1);

			timeline.play();

			this.isclosesubmanu = true;

		}
	}

	 public void closesubmanu() {
		 //TODO: コピペなのでどちらか1つにまとめる

		Timeline timeline = new Timeline();

		Line uline = (Line) submanu.getChildren().get(0);
		Line dline = (Line) submanu.getChildren().get(1);



		KeyValue kvu0 = new KeyValue(uline.endXProperty(), 0, Interpolator.LINEAR);
		KeyFrame kfu0 = new KeyFrame(Duration.seconds(0.3), kvu0);

		KeyValue kvu1 = new KeyValue(uline.endXProperty(), this.SUBMANUWIDTH);
		KeyFrame kfu1 = new KeyFrame(Duration.ZERO, kvu1);

		KeyValue kvd0 = new KeyValue(dline.endXProperty(), 0, Interpolator.LINEAR);
		KeyFrame kfd0 = new KeyFrame(Duration.seconds(0.3), kvd0);

		KeyValue kvd1 = new KeyValue(dline.endXProperty(), this.SUBMANUWIDTH);
		KeyFrame kfd1 = new KeyFrame(Duration.ZERO, kvd1);

		KeyValue kvp0 = new KeyValue(submanu.prefHeightProperty(), 0, Interpolator.LINEAR);
		KeyFrame kfp0 = new KeyFrame(Duration.seconds(0.4), kvp0);

		KeyValue kvp1 = new KeyValue(submanu.prefHeightProperty(), this.SUBHEIGHT);
		KeyFrame kfp1 = new KeyFrame(Duration.seconds(0.1), kvp1);


		timeline.getKeyFrames().add(kfu0);
		timeline.getKeyFrames().add(kfu1);

		timeline.getKeyFrames().add(kfd0);
		timeline.getKeyFrames().add(kfd1);

		timeline.getKeyFrames().add(kfp0);
		timeline.getKeyFrames().add(kfp1);

		timeline.play();

		this.isclosesubmanu = true;


		((AnchorPane)submanu.getChildren().get(2)).getChildren().clear();


	 }

	 private List<ActionCard> getactivecardlist() {
		 AnchorPane[] cardlist = {card0, card1, card2, card3};
		 List<Integer> activelist = new ArrayList<>();
		 int index = 0;
		 for(AnchorPane pane : cardlist) {
			 if(pane.getScaleX() > 0.55) {
				 activelist.add(index);
			 }
			 ++index;
		 }

		 List<ActionCard> activecardlist = new ArrayList<>();
		 for(int i=0; i<deck.maxcardnum; ++i) {
			 if(activelist.contains(i)) {
				 activecardlist.add(deck.cardlist[i]);
			 }
		 }

		 return activecardlist;
	 }



	 public void updatetype(Type type) {
		 List<ActionCard> activecardlist = getactivecardlist();
		 if(!activecardlist.isEmpty()) {
			 for(ActionCard card : activecardlist) {
				 card.type = type;
			 }
		 }
		 //表示の更新

		 updateUIInfo(deck);

	 }



}
