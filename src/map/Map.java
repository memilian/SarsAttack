/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.shape.Quad;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import java.util.ArrayList;



/**
 *
 * @author memilian
 */
public class Map {

    private Waypoint spawnPoint;
    private TerrainQuad terrain;
    

    public Map(AssetManager assetManager, Camera camera) {

        //Terrain materials :

        Material terrainMat = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");

        Texture heightMapImage = assetManager.loadTexture("/Textures/flatheightmap.png");
        Texture alphamap = assetManager.loadTexture("/Textures/alphamap.png");
        Texture road = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");

        grass.setWrap(Texture.WrapMode.Repeat);
        road.setWrap(Texture.WrapMode.Repeat);

        terrainMat.setTexture("Alpha", alphamap);
        terrainMat.setTexture("Tex1", grass);
        terrainMat.setTexture("Tex2", road);
        terrainMat.setFloat("Tex1Scale", 16);
        terrainMat.setFloat("Tex2Scale", 64);


        ImageBasedHeightMap heightMap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightMap.load();

        terrain = new TerrainQuad("terrain", 65, 1025, heightMap.getHeightMap());
        terrain.setMaterial(terrainMat);
        TerrainLodControl control = new TerrainLodControl(terrain, camera);
        control.setLodCalculator(new DistanceLodCalculator(65, 1.7f)); // patch size, and a multiplier
        terrain.addControl(control);
        //
        //          wp21        wp211
        //
        //  wp1     wp2          wp3        wp4
        //
        //          wp22        wp222
        //
        Waypoint wp1 = new Waypoint(new Vector3f(-300,0,0));
        Waypoint wp2 = new Waypoint(new Vector3f(-100,0,0));
        Waypoint wp21 = new Waypoint(new Vector3f(-100,0,100));
        Waypoint wp22 = new Waypoint(new Vector3f(-100,0,-100));
        Waypoint wp211 = new Waypoint(new Vector3f(140,0,100));
        Waypoint wp222 = new Waypoint(new Vector3f(140,0,-100));
        Waypoint wp3 = new Waypoint(new Vector3f(140,0,0));
        Waypoint wp4 = new Waypoint(new Vector3f(300,0,0));
        wp4.setIsLastWaypoint(true);
        
        wp1.addNextWaypoint(wp2);
        wp2.addNextWaypoint(wp21);
        wp2.addNextWaypoint(wp22);
        wp21.addNextWaypoint(wp211);
        wp22.addNextWaypoint(wp222);
        wp211.addNextWaypoint(wp3);
        wp222.addNextWaypoint(wp3);
        wp3.addNextWaypoint(wp4);
        spawnPoint = wp1;
        
        
        
    }

    public TerrainQuad getTerrain() {
        return this.terrain;
    }
    
    //The spawn point is the first Waypoint
    public Waypoint getSpawnPoint(){
        return spawnPoint;
    }
}
