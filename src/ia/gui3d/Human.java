package ia.gui3d;
/**
 *  Human.java  from "Java3d Jumpstart" ( modified by Paul Flavin )
 *
 *  note: You _can_ animate VRML  H-Anim Avatars  with Java3d and
 *  ----  this does _NOT_ animate H-Anim Avatars ( see below )
 */

/**
 * http://www.frontiernet.net/~imaging/java3d_and_vrml.html	// ~ real ~ H-Anim
 *
 * http://web3dbooks.com/java3d/jumpstart/Java3DExplorer.html	//  pseudo  H-Anim
 * http://web3dbooks.com/java3d/jumpstart/J3DJumpStart.zip		//
 *
 * http://www.frontiernet.net/~imaging/games_with_java3d.html	//  Games with Java3d
 * http://www.frontiernet.net/~imaging/H-Anim_Avatars.html		//  Interactive H-Anim
 * http://www.web3d.org/WorkingGroups/web3d-mpeg/hypermail/2001/0159.html
 * http://www.frontiernet.net/~imaging/sourcecode/Human.java	//  this file.
 *
 * Human with Controlled Arm using VRML Human Model datafile
 * ---------------------------------------------------------
 * http://www.frontiernet.net/~imaging/sourcecode/HumanWithControlledArm_VRML.java
 * http://www.frontiernet.net/~imaging/sourcecode/stickChickMinimal.wrl
 *
 * To compile & run on Linux box with a Java JDK installed
 * Type:  javac Human.java ; java Human
 * If you're running Microsoft Windows:  Get a Clue & Get Linux.
 *
 */


import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.swing.JFrame;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

@SuppressWarnings("serial")
public class Human extends JFrame {

	private SimpleUniverse u;

	private Canvas3D canvas;

	private BranchGroup objRoot;

	private TransformGroup Human_body, Human_skullbase; // H-Anim naming convention,

	private TransformGroup Human_r_shoulder, Human_r_elbow; // not H-Anim, inferior

	private TransformGroup Human_l_shoulder, Human_l_elbow; // If you're going to do it

	private int b1 = 0, b2 = 0, b3 = 0, ab = 0;

	private AxisAngle4f rShoulderAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);

	private AxisAngle4f rElbowAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);

	private AxisAngle4f rShoulderAAZ = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);

	private AxisAngle4f rShoulderAAY = new AxisAngle4f(0.0f, 1.0f, 0.0f, 0.0f);

	AxisAngle4f axisZ =new AxisAngle4f(0.0f, 0.0f, 1.0f, 0.0f);;

	AxisAngle4f axisY =new AxisAngle4f(0.0f, 1.0f, 0.0f, 0.0f);;

	AxisAngle4f axisX =new AxisAngle4f(1.0f, 0.0f, 0.0f, 0.0f);;

	public Cylinder tmpCyl;

	public Sphere mao, alvo_obj;
	
	public TransformGroup maoTg;

	private TransformGroup alvo;

	private Vector3f posInitial = new Vector3f();
	
	private Vector3f posAlvo = new Vector3f();

	// Temporaries that are reused
	private Transform3D tmpTrans = new Transform3D();

	private Vector3f tmpVector = new Vector3f();

	public Human() {
		super("Human 3D");

		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		canvas = new Canvas3D(config);

		add(canvas, BorderLayout.CENTER);

		u = new SimpleUniverse(canvas);

		// Create a simple scene and attach it to the virtual universe
		BranchGroup scene = createSceneGraph();
		
		// move the ViewPlatform back a bit so the objects in the scene can be viewed.
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);

		//View view = u.getViewer().getView();

		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		setSize(500, 500);
		// setVisible(true);
		//setMaximumScreenSize(true);
		validate();
		repaint();
	}

	private void createHuman() {

		Sphere tmpSphere;
		TransformGroup tmpTG;

		/** cores usadas para o corpo */
		Color3f red = new Color3f(0.0f, 0.0f, 1.0f);
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

		Human_body = new TransformGroup();
		Human_body.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_body.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		/** centro do corpo */
		tmpVector.set(0.0f, -1.5f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_body.setTransform(tmpTrans);

		// Set up an appearance to make the body with red ambient,
		// black emmissive, red diffuse and white specular coloring
		Material material = new Material(red, black, red, white, 50);
		Appearance appearance = new Appearance();
		appearance.setMaterial(material);

		// offset and place the cylinder for the body
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, 1.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.5f, 3.0f, appearance); // 0.75f,3.0f
		tmpTG.addChild(tmpCyl);

		// add the shape to the body
		Human_body.addChild(tmpTG);

		// create the r_shoulder TransformGroup
		Human_r_shoulder = new TransformGroup();
		Human_r_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_r_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		// translate from the waist
		tmpVector.set(-0.95f, 2.9f, -0.2f);
		tmpTrans.set(tmpVector);
		Human_r_shoulder.setTransform(tmpTrans);

		// place the sphere for the r_shoulder

		tmpSphere = new Sphere(0.22f, appearance);
		// Shape3D shape = tmpSphere.getShape();
		Human_r_shoulder.addChild(tmpSphere);

		// offset and place the cylinder for the r_shoulder
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance); // 0.2,1.0
		tmpTG.addChild(tmpCyl);

		// add the shape to the r_shoulder
		Human_r_shoulder.addChild(tmpTG);

		// add the shoulder to the body group
		Human_body.addChild(Human_r_shoulder);

		// create the r_elbow TransformGroup
		Human_r_elbow = new TransformGroup();
		Human_r_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_r_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, -1.054f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_r_elbow.setTransform(tmpTrans);

		// place the sphere for the r_elbow
		tmpSphere = new Sphere(0.22f, appearance);
		Human_r_elbow.addChild(tmpSphere);

		// offset and place the cylinder for the r_shoulder
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance); // 0.2,1.0
		tmpTG.addChild(tmpCyl);

		maoTg = new TransformGroup();
		maoTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		maoTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		maoTg.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		maoTg.setTransform(tmpTrans);
		mao = new Sphere(0.22f, appearance);
		mao.setBoundsAutoCompute(true);
		mao.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
		
		maoTg.addChild(mao);

		tmpTG.addChild(maoTg);

		// add the shape to the r_shoulder
		Human_r_elbow.addChild(tmpTG);

		// add the elbow to the shoulder group
		Human_r_shoulder.addChild(Human_r_elbow);

		// create the l_shoulder TransformGroup
		Human_l_shoulder = new TransformGroup();
		Human_l_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_l_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.95f, 2.9f, -0.2f);
		tmpTrans.set(tmpVector);
		Human_l_shoulder.setTransform(tmpTrans);

		// place the sphere for the l_shoulder
		tmpSphere = new Sphere(0.22f, appearance);
		Human_l_shoulder.addChild(tmpSphere);

		// offset and place the cylinder for the l_shoulder
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance);
		tmpTG.addChild(tmpCyl);

		// add the shape to the l_shoulder
		Human_l_shoulder.addChild(tmpTG);

		// add the shoulder to the body group
		Human_body.addChild(Human_l_shoulder);

		// create the r_elbow TransformGroup
		Human_l_elbow = new TransformGroup();
		Human_l_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_l_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, -1.054f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_l_elbow.setTransform(tmpTrans);

		// place the sphere for the l_elbow
		tmpSphere = new Sphere(0.22f, appearance);
		Human_l_elbow.addChild(tmpSphere);

		// offset and place the cylinder for the l_elbow
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance);
		tmpTG.addChild(tmpCyl);

		// add the shape to the l_elbow
		Human_l_elbow.addChild(tmpTG);

		// add the shoulder to the body group
		Human_l_shoulder.addChild(Human_l_elbow);

		TransformGroup maoEsquerda;
		maoEsquerda = new TransformGroup();
		maoEsquerda.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		maoEsquerda.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		maoEsquerda.setTransform(tmpTrans);
		Sphere maoEsq = new Sphere(0.22f, appearance);
		maoEsq.setBoundsAutoCompute(true);
				
		maoEsquerda.addChild(maoEsq);

		tmpTG.addChild(maoEsquerda);
		
		// create the skullbase TransformGroup
		Human_skullbase = new TransformGroup();
		Human_skullbase.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_skullbase.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		tmpVector.set(0.0f, 3.632f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_skullbase.setTransform(tmpTrans);

		// offset and place the sphere for the skull
		tmpSphere = new Sphere(0.5f, appearance);
		// add the shape to the l_shoulder	
		Human_skullbase.addChild(tmpSphere);
		
		// add the shoulder to the body group
		Human_body.addChild(Human_skullbase);

		alvo = criaAlvo();
	}

	public void lateralBracoDireito(int rotation) {
		b1 += rotation;
		rShoulderAA.angle = (float) Math.toRadians(rotation);
		Human_r_shoulder.getTransform(tmpTrans);
		Transform3D t = new Transform3D();
		t.setRotation(rShoulderAA);
		tmpTrans.mul(t);

		Human_r_shoulder.setTransform(tmpTrans);

		tmpTrans.get(posInitial);
		tmpTrans.transform(posInitial);
		getPosition();

	}

	public void anteBracoDireito(int rotation) {
		ab += rotation;
		
		// if (ab < 155 && ab > 0)
		//	return ;
		
		rElbowAA.angle = (float) Math.toRadians(rotation);
		Human_r_elbow.getTransform(tmpTrans);
		Transform3D t = new Transform3D();
		t.setRotation(rElbowAA);
		tmpTrans.mul(t);
		Human_r_elbow.setTransform(tmpTrans);
		tmpTrans.transform(posInitial);

		getPosition();
	}

	private void getPosition() {
		recuperaPosicao();
	}

	public Vector3f getPosicaoMao() {
		return posInitial;
	}
	
	public Vector3f getPosicaoAlvo() {
		return posAlvo;
	}
	
	public void recuperaPosicao()
	{
		mao.getLocalToVworld(tmpTrans);
		tmpTrans.get(posInitial);
		
		alvo_obj.getLocalToVworld(tmpTrans);
		tmpTrans.get(posAlvo);
	}
	
	// Alvo m�vel
	public void alvoY( float value ) {
		alvo.getTransform(tmpTrans);
		Transform3D t = new Transform3D();
		posAlvoY += value;
		Vector3f trans = new Vector3f(posAlvoX, posAlvoY, posAlvoZ);
		t.setTranslation(trans);
		tmpTrans.setTranslation(trans);
		alvo.setTransform(tmpTrans);
		recuperaPosicao();
	}
	
	private float posAlvoX, posAlvoY, posAlvoZ;

	public void alvoX( float value ) {
		alvo.getTransform(tmpTrans);
		Transform3D t = new Transform3D();
		posAlvoX += value;
		Vector3f trans = new Vector3f(posAlvoX, posAlvoY, posAlvoZ);
		t.setTranslation(trans);
		tmpTrans.setTranslation(trans);
		alvo.setTransform(tmpTrans);
		recuperaPosicao();
	}
		
	public void alvoZ( float value ) {
		alvo.getTransform(tmpTrans);
		Transform3D t = new Transform3D();
		posAlvoZ += value;
		Vector3f trans = new Vector3f(posAlvoX, posAlvoY, posAlvoZ);
		t.setTranslation(trans);
		tmpTrans.setTranslation(trans);
		alvo.setTransform(tmpTrans);
		recuperaPosicao();
	}
	
	public void cimaBracoDireito(int rotation) {
		b2 += rotation;
		
		//if (b2 > 180 && b2 > -50)
		//    return ;
		
		rShoulderAAZ.angle = (float) Math.toRadians(rotation);
		Human_r_shoulder.getTransform(tmpTrans);
		Transform3D t = new Transform3D();
		t.setRotation(rShoulderAAZ);
		tmpTrans.mul(t);
		// tmpTrans.setRotation(new AxisAngle4f( 0.0f, 0.0f,0.0f,
		// rShoulderAAY.angle ));
		// tmpTrans.mul(tmpTrans);
		Human_r_shoulder.setTransform(tmpTrans);

		tmpTrans.get(posInitial);
		tmpTrans.transform(posInitial);
		getPosition();
	}

	public void rotacionaBracoDireito(int rotation) {
		b3 += rotation;
	
		//if (b3 < 45 && b3 > -45)
		//	return ;
		
		rShoulderAAY.angle = (float) Math.toRadians(rotation);
		Human_r_shoulder.getTransform(tmpTrans);
		Transform3D t = new Transform3D();
		t.setRotation(rShoulderAAY);
		tmpTrans.mul(t);
		Human_r_shoulder.setTransform(tmpTrans);

		tmpTrans.get(posInitial);
		tmpTrans.transform(posInitial);
		getPosition();
	}

//	public void actionPerformed(ActionEvent e) {
//		if(false){
//		} else if (e.getSource() == buttonUp) {
//			if (b2 < 180)
//				cimaBracoDireito(value);
//		} else if (e.getSource() == buttonDown) {
//			if (b2 > -50)
//				cimaBracoDireito(value * -1);
//		} else if (e.getSource() == buttonLeft) {
//			if (b1 > 0)
//				lateralBracoDireito(value * -1);
//		} else if (e.getSource() == buttonRight) {
//			if (b1 < 90)
//				lateralBracoDireito(value);
//		} else if (e.getSource() == buttonLeftRotation) {
//			if (b3 < 45)
//				rotacionaBracoDireito(value);
//		} else if (e.getSource() == buttonRightRotation) {
//			if (b3 > -45)
//				rotacionaBracoDireito(value * -1);
//		} else if (e.getSource() == buttonUpBA) {
//			if (ab < 155)
//				anteBracoDireito(value);
//		} else if (e.getSource() == buttonDownBA) {
//			if (ab > 0)
//				anteBracoDireito(value * -1);
//		}
//	}

	private BranchGroup createSceneGraph() {
		// Create the root of the branch graph
		objRoot = new BranchGroup();
		objRoot.setCapability(BranchGroup.ALLOW_DETACH);
		objRoot.setCapability(BranchGroup.ALLOW_BOUNDS_READ);

		// Create a TransformGroup to scale the scene down by 3.5x
		// TODO: move view platform instead of scene using orbit behavior
		TransformGroup objScale = new TransformGroup();
		Transform3D scaleTrans = new Transform3D();

		float scaleFactor = 1 / 3.5f; // was 1 / 2.0f
		scaleTrans.set(scaleFactor); // scale down

		objScale.setTransform(scaleTrans);
		objRoot.addChild(objScale);

		// Create a TransformGroup and initialize it to the
		// identity. Enable the TRANSFORM_WRITE capability so that
		// the mouse behaviors code can modify it at runtime. Add it to the
		// root of the subgraph.
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objScale.addChild(objTrans);

		// Add the primitives to the scene

		createHuman(); // the human
		objTrans.addChild(Human_body);
		objTrans.addChild(alvo);

		BoundingSphere bounds = new BoundingSphere(new Point3d(), 50.0); // 100.0

		Background bg = new Background(new Color3f(1.0f, 1.0f, 1.0f));
		bg.setApplicationBounds(bounds);
		objTrans.addChild(bg);

		// set up the mouse rotation behavior
		MouseRotate mr = new MouseRotate();
		mr.setTransformGroup(objTrans);
		mr.setSchedulingBounds(bounds);
		mr.setFactor(0.007);
		objTrans.addChild(mr);

		// Set up the ambient light
		Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);

		// Set up the directional lights
		Color3f light1Color = new Color3f(0.9f, 0.9f, 0.9f);// (1.0f, 1.0f,
															// 1.0f);
		Vector3f light1Direction = new Vector3f(-1.0f, -1.0f, -1.0f);// (0.0f,
																		// -0.2f,
																		// -1.0f)
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		return objRoot;
	}

	private TransformGroup criaAlvo() {
	//	java.net.URL texturaAlvo = null;

//		try {
//			System.out.println("Data Dir = "+dataDir);
//			String dataDirName = ClassLoader.getSystemResource(dataDir).getPath();
//			System.out.println("dirName = "+dataDirName);
//			String urlName = dataDirName + "/" + textureName;
//
//			texturaAlvo = new java.net.URL("jar:"+urlName);
//		}
//		catch (java.net.MalformedURLException ex) {
//			System.out.println("N�o conseguiu carregar textura alvo:"+ex.getMessage()+"  "+texturaAlvo);
//			System.exit(1);
//		}
//		Appearance app = criaAparencia(texturaAlvo);
// return criaEsfera(app, 1 / 3.5f, 2.0364738, 2.2727242, -0.2, true);
// return criaEsfera(app, 1 / 3.5f, -0.43198764, 3.047063-3.6, -0.2, true);
 	
		//return criaEsfera(null, 1 / 3.5f, -2,0.8,-0.2, true);
		return criaEsfera(null, 1 / 3.5f, -2 ,1.0,1.5, true);
	}

//	/** M�todo de apoio para a cria��o de apar�ncias baseadas em textura */
//	private Appearance criaAparencia(java.net.URL textura) {
//
//		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
//		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
//
//		Appearance app = new Appearance();
//
//		// Utilizar� uma arquivo .jpg para definir a textura do bra�o.
//		TextureLoader tex = new TextureLoader(textura, this);
//		app.setTexture(tex.getTexture());
//
//		// Propriedades do material
//		app.setMaterial(new Material(white, black, white, black, 1.0f));
//		TextureAttributes texAttr = new TextureAttributes();
//		texAttr.setTextureMode(TextureAttributes.MODULATE);
//		app.setTextureAttributes(texAttr);
//
//		return app;
//	}

	/**
	 * app: Apar�ncia do Objeto escala: Tamanho do objeto xpos: Coordenada x do
	 * Objeto ypos: Coordenada y do Objeto rotacionar: true se objeto deve girar
	 * em torno do pr�prio eixo.
	 */
	private TransformGroup criaEsfera(Appearance app, double escala,
			double xpos, double ypos, double zpos, boolean rotacionar) {

		// Create a transform group node to escala and position the object.
		Transform3D t = new Transform3D();
		// t.set(0.2f, new Vector3f(-0.57f, 0.28f, 0.42f));
		
		posAlvoX = -2.0f;
		posAlvoY = 1.0f;
		posAlvoZ = 1.5f;
		
		t.set(0.2f, new Vector3f(posAlvoX ,posAlvoY,posAlvoZ));
		
		TransformGroup objTrans = new TransformGroup(t);

		// Create a second transform group node and initialize it to the
		// identity. Enable the TRANSFORM_WRITE capability so that
		// our behavior code can modify it at runtime.
		TransformGroup spinTg = new TransformGroup();
		spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		alvo_obj = (Sphere) new Sphere(1.0f, Sphere.GENERATE_NORMALS
				| Sphere.GENERATE_TEXTURE_COORDS, 20, app);
		
		alvo_obj.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
		
		// add it to the scene graph.
		spinTg.addChild(alvo_obj);

//		if (rotacionar) {
//			// Create a new Behavior object that will perform the desired
//			// operation on the specified transform object and add it into
//			// the scene graph.
		Transform3D yAxis = new Transform3D();
		Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,5000, 0, 0, 0, 0, 0);
//
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, spinTg, yAxis, 0.0f, (float) Math.PI * 2.0f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,0.0), 100.0);
//
		rotator.setSchedulingBounds(bounds);
		objTrans.addChild(rotator);
//		}

		objTrans.addChild(spinTg);

		
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		return objTrans;
	}

//	public void montaCaminho(Nodo n) {
//		if (n == null) {
//			return;
//		}
//		montaCaminho(n.pai);
//		String op = ((StateArm) n.state).op;
//		if (op.equals("DIRETABRACO")) {
//			lateralBracoDireito(value);
//		} else if (op.equals("ESQUERDABRACO")) {
//			lateralBracoDireito(value * -1);
//		} else if (op.equals("CIMAANTEBRACO")) {
//			anteBracoDireito(value);
//		} else if (op.equals("BAIXOANTEBRACO")) {
//			anteBracoDireito(value * -1);
//		} else if (op.equals("CIMABRACO")) {
//			cimaBracoDireito(value);
//		} else if (op.equals("BAIXOBRACO")) {
//			cimaBracoDireito(value * -1);
//		} else if (op.equals("ROTACIONADIRETABRACO")) {
//			rotacionaBracoDireito(value);
//		} else if (op.equals("ROTACIONAESQUERDABRACO")) {
//			rotacionaBracoDireito(value * -1);
//		}
//		try {
//			Thread.sleep(350);
//		} catch (Exception e) {
//		}
//	}

	// Testa
//	public static void main(String...args) {
//		Human h = new Human();
//	}

	public class MovimentoBracoBehavior extends Behavior {

		private TransformGroup target;

		private double angle = 0.0;

		private Transform3D rotate = new Transform3D();

		private WakeupOnAWTEvent condition;

		public MovimentoBracoBehavior( TransformGroup target) {
			//condition = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
			this.target = target;
		}

		@Override
		public void initialize() {
			this.wakeupOn(condition);
		}

		@Override
		public void processStimulus(Enumeration arg0) {
			AWTEvent[] events = condition.getAWTEvent();
			if(events.length==1) {
				KeyEvent event = (KeyEvent) events[0];
				Transform3D tempRotate = new Transform3D();
				switch(event.getKeyCode()){
					case 38: // UP
						angle+=0.01;
						target.getTransform(rotate);
						axisZ.angle = (float) Math.toRadians(10); // 30o
						tempRotate.setRotation(axisZ);
						rotate.mul(tempRotate);
					break;
					case 40: // Down
						angle+=0.01;
						target.getTransform(rotate);
						axisZ.angle = (float) Math.toRadians(-10); // 30o
						tempRotate.setRotation(axisZ);
						rotate.mul(tempRotate);
						break;
					case 37:
						angle+=0.01;
						target.getTransform(rotate);
						axisY.angle = (float) Math.toRadians(10); // 30o
						tempRotate.setRotation(axisZ);
						rotate.mul(tempRotate);
					case 39:
						angle+=0.01;
						target.getTransform(rotate);
						axisX.angle = (float) Math.toRadians(-10); // 30o
						tempRotate.setRotation(axisZ);
						rotate.mul(tempRotate);
						break;
					default: break;
				}
				target.setTransform(rotate);
				this.wakeupOn(condition);
			}
		}

	}
}
