package fjr.test.kalkulus.optimisasi;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;




public class CrossInsideACircle extends  Application {

	Group root; 
	
	final double radiusLingkaran = 150; 
	
	double rootWidth = 700; 
	double rootheight = 400; 
	
	double canvasHeight = rootheight; 
	double canvasWidth = 700; 

	
	GraphicsContext gc; 
	GraphicsContext gc2 ; 
	
	Cross mainCross; 
	
	double curvaPLotSudut[]; 
	double curvaPlotLuas[]; 
	
	int numArrayCurvaPlot = 400; 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		AnchorPane root = new AnchorPane(); 
		
		root.setPrefSize(rootWidth, rootheight);
		
		
		HBox box = new HBox(); 
		
		
		Canvas canvas = new Canvas(canvasWidth, canvasHeight); 
		
		gc = canvas.getGraphicsContext2D(); 
		
		box.getChildren().add(canvas); 
				
		root.getChildren().add(box); 
		
		Scene sc = new Scene(root); 
		
		primaryStage.setScene(sc); 

		curvaPLotSudut = new double[numArrayCurvaPlot]; 
		curvaPlotLuas = new double[numArrayCurvaPlot]; 
		
		initCross(); 
		repaint(gc, mainCross );
		
		root.getChildren().add(getControl());
		
		
		
		primaryStage.show();
	}
	
	
	private VBox getControl(){
		VBox box = new VBox(); 
		
		box.setTranslateX(300);
		box.setTranslateY(10);
		
		
		final Slider slider = new Slider(); 
		slider.setOrientation(Orientation.HORIZONTAL);
		slider.setPrefWidth(100 );
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						double scale = slider.getValue()/100.0 *  (Math.PI/2.0) ; 
						mainCross.setSudut(scale);
						repaint(gc, mainCross);
					}
				});
		
		slider.setValue(mainCross.getSudut()/(Math.PI/2.0) * 100.0);
		box.getChildren().add(slider);
		
		return box; 
	}
	
	private void initCross(){
		Cross cross = new Cross(180,200, Math.PI/4.0); 
		this.mainCross = cross; 
		
		double step = Math.PI/2.0 / numArrayCurvaPlot; 
		
		
		double sudut_ = 0; 
		for(int i=0; i < curvaPLotSudut.length; i++){
			cross.setSudut(sudut_); 
			curvaPLotSudut[i] = sudut_; 
			curvaPlotLuas[i] = cross.getLuasCross(); 
//			System.out.println(curvaPLot)
			sudut_+= step; 
		}		
	}
	
	
	static double getTemporarySudut(double sudut){
		return  ( Math.PI/2.0  - sudut )/ 2.0 ;
	}
	
	
	private class Cross{
		
		double width; 
		double height; 
		
		double sudut; 

		double titikAsalX; 
		double titikAsalY;
		
		double temporarySudut ;
		
		double sisiKanan; 
		double sisiKiri; 
		double sisiAtas; 
		double sisiBawah; 
		
		double panjangCross; 
		double lebarCross; 
		
		
		// sisi-sisi dari kros 
		double[]  kananAtas; 
		double[]  kananBawah; 
		double[]  kananVertical; 
		
		double[]  kiriAtas; 
		double[]  kiriBawah; 
		double[]  kiriVertical;
		
		double[] atasKiri; 
		double[] atasKanan; 
		double[] atasHorizontal; 
		
		double[] bawahKiri; 
		double[] bawahKanan; 
		double[] bawahHorizontal; 
		
		
		// untuk Memberi visualisasi tentang sudutnya... 
		double[] garisSudutUp; 
		double[] garisSudutBottom; 
		
		double luasCross; 
		
//		double radiusLingkaran; 

		public Cross(double titikAsalX, double titikAsalY, double sudut){
			this.titikAsalX = titikAsalX; 
			this.titikAsalY = titikAsalY; 
			this.sudut = sudut;
			this.temporarySudut = ( Math.PI/2.0  - sudut )/ 2.0 ; 
			
			kananAtas = new double[4]; 
			kananBawah = new double[4]; 
			kananVertical = new double[4]; 
			
			kiriAtas = new double[4]; 
			kiriBawah = new double[4]; 
			kiriVertical = new double[4]; 
			
			atasKiri = new double[4]; 
			atasKanan = new double[4]; 
			atasHorizontal = new double[4]; 
			
			bawahKiri = new double[4]; 
			bawahKanan = new double[4]; 
			bawahHorizontal = new double[4]; 
			
			garisSudutBottom = new double[4]; 
			garisSudutUp = new double[4]; 
			
			
			setSudut(sudut);
		}
		
		
		public double getSudut(){
			return sudut; 
		}
		
//		public  double convertToRadian(double sudut){
//			return sudut/180 * Math.PI;
//		}
//		
		public double getTemporarySudut(){
			return temporarySudut; 
		}
		
		public void setSudut(double sudut){
			
//			this.sudut = convertToRadian(sudut); 
			this.sudut = sudut; 
			this.temporarySudut =  CrossInsideACircle.getTemporarySudut(sudut) ; 
			
			panjangCross =  radiusLingkaran * Math.cos(temporarySudut) -  
					radiusLingkaran * Math.sin(temporarySudut); 
					
			sisiKanan = titikAsalX + radiusLingkaran * Math.sin(temporarySudut); 
			sisiKiri = titikAsalX - radiusLingkaran * Math.sin(temporarySudut);  
			
			sisiAtas = titikAsalY  - radiusLingkaran * Math.sin(temporarySudut); 
			sisiBawah = titikAsalY + radiusLingkaran * Math.sin(temporarySudut); 

			
			kananAtas[0] = sisiKanan; //x1
			kananAtas[1] = sisiKanan + panjangCross ; // x2 
			kananAtas[2] = sisiAtas;  // y1 
			kananAtas[3] = sisiAtas;  // y2 
			
			kananBawah[0] = sisiKanan; 
			kananBawah[1] = sisiKanan + panjangCross; 
			kananBawah[2] = sisiBawah;
			kananBawah[3] = sisiBawah; 
			
			kananVertical[0] = sisiKanan + panjangCross; 
			kananVertical[1] = sisiKanan + panjangCross; 
			kananVertical[2] = sisiAtas; 
			kananVertical[3] = sisiBawah; 
			
			kiriAtas[0] = sisiKiri; 
			kiriAtas[1] = sisiKiri - panjangCross; 
			kiriAtas[2] = sisiAtas; 
			kiriAtas[3] = sisiAtas;
			
			
			kiriBawah[0] = sisiKiri; 
			kiriBawah[1] = sisiKiri - panjangCross; 
			kiriBawah[2] =  sisiBawah; 
			kiriBawah[3] = sisiBawah; 
			
			kiriVertical[0] = sisiKiri - panjangCross; 
			kiriVertical[1] = sisiKiri - panjangCross; 
			kiriVertical[2] = sisiAtas; 
			kiriVertical[3] = sisiBawah; 
			
			atasKiri[0] = sisiKiri;
			atasKiri[1] = sisiKiri; 
			atasKiri[2] = sisiAtas; 
			atasKiri[3] = sisiAtas - panjangCross; 
			
			atasKanan[0] = sisiKanan; 
			atasKanan[1] = sisiKanan; 
			atasKanan[2] = sisiAtas; 
			atasKanan[3] = sisiAtas - panjangCross; 
			
			atasHorizontal[0] = sisiKiri; 
			atasHorizontal[1] = sisiKanan; 
			atasHorizontal[2] =  sisiAtas - panjangCross; 
			atasHorizontal[3] = sisiAtas - panjangCross; 
			
			bawahKiri[0] = sisiKiri; 
			bawahKiri[1] = sisiKiri; 
			bawahKiri[2] = sisiBawah; 
			bawahKiri[3] = sisiBawah + panjangCross; 
			
			bawahKanan[0] = sisiKanan; 
			bawahKanan[1] = sisiKanan;
			bawahKanan[2] = sisiBawah; 
			bawahKanan[3] = sisiBawah + panjangCross; 	
			
			bawahHorizontal[0] = sisiKiri; 
			bawahHorizontal[1] = sisiKanan; 
			bawahHorizontal[2] = sisiBawah + panjangCross; 
			bawahHorizontal[3] = sisiBawah + panjangCross; 
			
			garisSudutUp[0] = titikAsalX; 
			garisSudutUp[1] = sisiKanan; 
			garisSudutUp[2] = titikAsalY ;
			garisSudutUp[3] = sisiAtas - panjangCross; 
			
			
			garisSudutBottom[0] = titikAsalX; 
			garisSudutBottom[1] = sisiKanan + panjangCross ; 
			garisSudutBottom[2] = titikAsalY; 
			garisSudutBottom[3] = sisiAtas; 

			
			hitungLuas();
		}
		
		
		public double[] getGarisSudutUp(){
			return garisSudutUp; 
		}
		
		public double[] getGarisSudutBottom(){
			return garisSudutBottom; 
		}
		
		public void hitungLuas(){
			double lebarCross = 2 * radiusLingkaran * Math.sin(temporarySudut); 
			luasCross = 4*panjangCross * lebarCross + lebarCross * lebarCross;
		}
		
		public double getLuasCross(){
			return luasCross; 
		}
		public double getTitikAsalX(){
			return titikAsalX; 
		}
		
		public double getTitikAsalY(){
			return titikAsalY; 
		}
		
		public double[] getKananAtas(){
			return kananAtas; 
		}
		
		public double[] getKananBawah(){
			return kananBawah; 
		}
		
		public double[] getKananVertical(){
			return kananVertical; 
		}
		
		public double[] getKiriAtas(){
			return kiriAtas; 
		}
		
		public double[] getKiriBawah(){
			return kiriBawah; 
		}
		
		public double[] getKiriVertical(){
			return kiriVertical; 
		}
		
		public double[] getAtasKanan(){
			return atasKanan; 
		}
		
		public double[] getAtasKiri(){
			return atasKiri; 
		}
		
		public double[] getAtasHorizontal(){
			return atasHorizontal; 
		}
		
		public double[] getBawahKiri(){
			return bawahKiri; 
		}
		
		public double[] getBawahKanan(){
			return bawahKanan; 
		}
		
		public double[] getBawahHorizontal(){
			return bawahHorizontal; 
		}
	}
	
	
	private void repaint(GraphicsContext gc, Cross cross ){
		gc.setFill(Color.WHITE); 
		gc.fillRect(0, 0, canvasWidth/2.0, canvasHeight); 
		
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1); 
		
		// pertama gambar lingkaran
		gc.strokeOval(cross.getTitikAsalX() - radiusLingkaran, cross.getTitikAsalY() -radiusLingkaran , radiusLingkaran *2, radiusLingkaran * 2);
		
		gc.strokeLine(cross.getKananAtas()[0], cross.getKananAtas()[2], cross.getKananAtas()[1], cross.getKananAtas()[3]); // gambar kanan atas
		gc.strokeLine(cross.getKananBawah()[0], cross.getKananBawah()[2], cross.getKananBawah()[1], cross.getKananBawah()[3]); // gambar kanan bawah
		gc.strokeLine(cross.getKananVertical()[0], cross.getKananVertical()[2], cross.getKananVertical()[1], cross.getKananVertical()[3]); 
		
		gc.strokeLine(cross.getKiriAtas()[0],cross.getKiriAtas()[2], cross.getKiriAtas()[1], cross.getKiriAtas()[3]); 
		gc.strokeLine(cross.getKiriBawah()[0], cross.getKiriBawah()[2], cross.getKiriBawah()[1], cross.getKiriBawah()[3]); 
		gc.strokeLine(cross.getKiriVertical()[0], cross.getKiriVertical()[2], cross.getKiriVertical()[1], cross.getKiriVertical()[3]);
		
		gc.strokeLine(cross.getAtasKanan()[0], cross.getAtasKanan()[2], cross.getAtasKanan()[1], cross.getAtasKanan()[3]); 
		gc.strokeLine(cross.getAtasKiri()[0], cross.getAtasKiri()[2], cross.getAtasKiri()[1], cross.getAtasKiri()[3]); 
		gc.strokeLine(cross.getAtasHorizontal()[0], cross.getAtasHorizontal()[2], cross.getAtasHorizontal()[1], cross.getAtasHorizontal()[3]) ;

		gc.strokeLine(cross.getBawahKiri()[0], cross.getBawahKiri()[2], cross.getBawahKiri()[1], cross.getBawahKiri()[3]); 
		gc.strokeLine(cross.getBawahKanan()[0], cross.getBawahKanan()[2], cross.getBawahKanan()[1], cross.getBawahKanan()[3]); 
		gc.strokeLine(cross.getBawahHorizontal()[0], cross.getBawahHorizontal()[2], cross.getBawahHorizontal()[1], cross.getBawahHorizontal()[3]);

		
		gc.setStroke(Color.MAGENTA); 
		gc.strokeLine(cross.getGarisSudutUp()[0], cross.getGarisSudutUp()[2], cross.getGarisSudutUp()[1], cross.getGarisSudutUp()[3]); 
		gc.strokeLine(cross.getGarisSudutBottom()[0], cross.getGarisSudutBottom()[2], cross.getGarisSudutBottom()[1], cross.getGarisSudutBottom()[3]); 
		
		repaintCanvasGraph(cross);
	}
	
	double anchorX = 370 ; 
	double anchorY = 280 ; 
	double deltaX = 300 ; 
	double deltaY = 170; 
	
	private void repaintCanvasGraph(Cross cross){
		gc.setFill(Color.LAVENDER); 
		gc.fillRect(canvasWidth/2.0, 0, canvasWidth/2.0, canvasHeight);
		
		gc.setStroke(Color.MAGENTA);
		gc.setLineWidth(2);
		gc.strokeLine(anchorX , anchorY, anchorX + deltaX, anchorY); // horizontal
		gc.strokeLine( anchorX, anchorY, anchorX, anchorY - deltaY);  // vertical
		
		double luas = getScaleLuas(cross.getLuasCross()); 
		double sudut = getScaleSudut(cross.getSudut()); 
		
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(4);
		gc.strokeLine(anchorX + sudut , anchorY, anchorX+ sudut , anchorY - luas); 
		
		//tampilkan trace curva luas sebagai fungsi sudut.. 
		gc.setFill(Color.LIGHTBLUE);
		for(int i=0; i<curvaPLotSudut.length ;i++){
			double luas_1 = getScaleLuas(curvaPlotLuas[i]); 
			double sudut_1 = getScaleSudut(curvaPLotSudut[i]); 
			gc.fillOval(anchorX+ sudut_1, anchorY - luas_1,2, 2);
		}
	}
	
	
	public double getScaleLuas(double luas){
		return luas / 60000 * deltaY; 
	}
	
	public double getScaleSudut(double sudut){
		return 	sudut/ (Math.PI/2.0) * deltaX ; 
	}
	

	public static void main(String[] args){
		launch(args); 
	}

}
