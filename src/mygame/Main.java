package mygame;

import assets.SrasAssetManager;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import entities.EffectType;
import com.jme3.util.SkyFactory;
import entities.EntityManager;
import entities.Person;
import entities.TargetMode;
import entities.Turret;
import map.Map;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private Map map;
    private static Main mainInstance;
    private AppGui appGui;

    /**
     * @return the mainInstance
     */
    public static Main getMainInstance() {
        return mainInstance;
    }
    private AppSettings mySettings;

    public void InitMain() {
        mySettings = new AppSettings(true);
        mySettings.setFrameRate(35);
    }

    public static void main(String[] args) {
        mainInstance = new Main();
        mainInstance.InitMain();
        ConfigurationWindow confWindow = new ConfigurationWindow(mainInstance);
        confWindow.setVisible(true);
    }

    /*
     @Override
     public void start()
     {
        
     }
     */
    @Override
    public void simpleInitApp() {

        stateManager.attach(
                new AbstractAppState() {
            @Override
            public void initialize(AppStateManager stateManager, Application app) {
                super.initialize(stateManager, app);
                redefineKeys();
                stateManager.detach(this);
            }
        });

        this.appGui = new AppGui(this);

        guiNode.attachChild(this.appGui.getStartButton());
        guiNode.attachChild(this.appGui.getPauseButton());
        guiNode.attachChild(this.appGui.getResumeButton());
        guiNode.attachChild(this.appGui.getTowerButton());

        map = new Map(assetManager, this.cam);
        SrasAssetManager.getInstance().Initialize(assetManager);

        Turret t = new Turret(Vector3f.ZERO.clone(), 200, 100, 110, 10, 1, 1, false, TargetMode.Nearest, new EffectType[]{EffectType.HEAL}, "toto");
        EntityManager.getInstance().registerTurret(t);

        DirectionalLight light = new DirectionalLight();
        light.setDirection((new Vector3f(-0.5f, -1f, -0.5f)).normalize());
        rootNode.addLight(light);
        rootNode.attachChild(map.getTerrain());
        rootNode.attachChild(EntityManager.getInstance().getEntityNode());
        rootNode.attachChild(SrasAssetManager.getInstance().getBuildingModel());

        Spatial sky = SkyFactory.createSky(assetManager, "Models/Skybox.dds", false);
        cam.setLocation(new Vector3f(50, 100, 300));
        cam.lookAt(Vector3f.UNIT_Z.negate().mult(100), Vector3f.UNIT_Y);
        //flyCam.setEnabled(false);
        rootNode.attachChild(sky);
        initCrossHairs();
        defineKeys();
    }
    //tmp
    long spawnTimer = 0;
    int spawnRate = 300;

    @Override
    public void simpleUpdate(float tpf) {
        if (System.currentTimeMillis() > spawnTimer + spawnRate) {
            Person stub = new Person(map.getSpawnPoint());
            EntityManager.getInstance().registerEntity(stub);
            spawnTimer = System.currentTimeMillis();
        }
        EntityManager.getInstance().onUpdate(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    //Unregister default camera mappings and make new ones
    private void redefineKeys() {
        inputManager.deleteMapping("FLYCAM_Forward");
        inputManager.deleteMapping("FLYCAM_Backward");
        inputManager.deleteMapping("FLYCAM_Lower");
        inputManager.deleteMapping("FLYCAM_StrafeLeft");
        inputManager.deleteMapping("FLYCAM_Rise");

        inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("FLYCAM_Lower", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("FLYCAM_Rise", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addListener(flyCam, new String[]{"FLYCAM_Backward", "FLYCAM_Forward", "FLYCAM_Lower", "FLYCAM_StrafeLeft", "FLYCAM_Rise"});
        flyCam.setMoveSpeed(50f);
        flyCam.setZoomSpeed(0);
    }

    /**
     * @return the mySettings
     */
    public AppSettings getMySettings() {
        return mySettings;
    }

    /**
     * A centred plus sign to help the player aim.
     */
    protected void initCrossHairs() {

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation(settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    private void defineKeys() {
        inputManager.addMapping("placeTurret", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "placeTurret");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {

            if (name.equals("placeTurret") && !isPressed) {
                //ChunkManager.removeVoxelAt(selectionPosition);
                placeTurret();
            }
        }
    };

    private void placeTurret() {

        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        map.getTerrain().collideWith(ray, results);

        if (results.size() != 1) {
            return;
        }

        float dist = results.getCollision(0).getDistance();
        Vector3f point = results.getCollision(0).getContactPoint();
        /* Geometry geom = SrasAssetManager.getInstance().makeBox(new Vector3f(10, 10, 10));
         geom.setLocalTranslation(point);
         Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         mat.setColor("Color", ColorRGBA.Yellow);

         mat.getAdditionalRenderState().setWireframe(true);
         mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Back);
         geom.setMaterial(mat);
         geom.setCullHint(Spatial.CullHint.Never);
         rootNode.attachChild(geom);*/
        Turret t = new Turret(point, 200, 100, 110, 10, 1, 1, false, TargetMode.Nearest, new EffectType[]{EffectType.REGEN}, "toto");
        EntityManager.getInstance().registerTurret(t);
    }
}