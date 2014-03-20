/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import javax.vecmath.Matrix3d;

/**
 *
 * @author memilian
 */
public class SrasAssetManager {

    static private SrasAssetManager instance;
    private Spatial turretModel;
    private Spatial personModel;
    private Spatial projModel;
    private Spatial buildingModel;
    private AssetManager assetManager;

    public static SrasAssetManager getInstance() {
        if (instance == null) {
            instance = new SrasAssetManager();
        }
        return instance;
    }

    public void Initialize(AssetManager assetManager) {
        //Stub
        this.assetManager = assetManager;
        Matrix3f matrix = new Matrix3f();
        matrix.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y.clone());

        turretModel = assetManager.loadModel("Models/Teapot/Teapot.obj");
        Material defaultMat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        turretModel.setMaterial(defaultMat);
        turretModel.setLocalScale(15f);
        personModel = makeBox(new Vector3f(5, 10, 5));
        projModel = makeBox(new Vector3f(3, 3, 3));
        buildingModel = assetManager.loadModel("Models/building.obj");
        buildingModel.setLocalScale(1f);
        buildingModel.setLocalTranslation(new Vector3f(-250, 0, 0));
        buildingModel.setLocalRotation(matrix);
    }

    public Spatial getTurretModel() {
        return turretModel;
    }

    public Spatial getProjModel() {
        return projModel;
    }

    public Spatial getPersonModel() {
        return personModel;
    }
    
    public Spatial getBuildingModel() {
        return buildingModel;
    }
    
    //Stub method
    public Geometry makeBox(Vector3f dimensions) {
        Box b = new Box(Vector3f.ZERO.clone(), dimensions);

        Geometry geom = new Geometry("box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        mat.getAdditionalRenderState().setWireframe(false);
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Back);
        geom.setMaterial(mat);
        geom.setCullHint(Spatial.CullHint.Never);

        return geom;
    }
}
