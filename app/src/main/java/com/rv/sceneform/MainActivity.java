package com.rv.sceneform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArFragment arFragment;
    //private Renderable model;
    private ModelRenderable mitem1, mitem2, mitem3, mitem4, mitem5,mitem6,mitem7,
                            mitem8,mitem9,mitem10,mitem11;

    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,
                imageView7,imageView8,imageView9,imageView10,imageView11;

    View arrayView[];
    int selected = 1;

    //private ViewRenderable viewRenderable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        imageView1 = (ImageView) findViewById(R.id.item1);
        imageView2 = (ImageView) findViewById(R.id.item2);
        imageView3 = (ImageView) findViewById(R.id.item3);
        imageView4 = (ImageView) findViewById(R.id.item4);
        imageView5 = (ImageView) findViewById(R.id.item5);
        imageView6 = (ImageView) findViewById(R.id.item6);
        imageView7 = (ImageView) findViewById(R.id.item7);
        imageView8 = (ImageView) findViewById(R.id.item8);
        imageView9 = (ImageView) findViewById(R.id.item9);
        imageView10 = (ImageView) findViewById(R.id.item10);
        imageView11 = (ImageView) findViewById(R.id.item11);

        setArrayView();
        setClickListener();

        setupModel();

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    createModel(anchorNode,selected);
            }
        });
    }

    private void setupModel() {

        ModelRenderable.builder()
                .setSource(this,R.raw.paint1)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem1 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model1", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint2)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem2 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model2", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint3)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem3 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model3", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint4)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem4 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model Vase", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint5)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem5 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model5", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint6)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem6 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model6", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint7)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem7 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model Golden", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint8)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem8 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model8", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint9)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem9 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model9", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,R.raw.paint10)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem10 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model10", Toast.LENGTH_LONG).show();
                    return null;
                });


        ModelRenderable.builder()
                .setSource(this, Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Lion/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> mitem11 = renderable )
                .exceptionally(throwable -> {
                    Toast.makeText(
                            this, "Unable to load model Lion", Toast.LENGTH_LONG).show();
                    return null;
                });

/*        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);
        ViewRenderable.builder()
                .setView(this, R.layout.view_model_title)
                .build()
                .thenAccept(viewRenderable -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.viewRenderable = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load View model", Toast.LENGTH_LONG).show();
                    return null;
                });*/

    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if (selected == 1){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.setParent(anchorNode);
            model1.setRenderable(mitem1);
            model1.select();

            addDetails(anchorNode,model1,"Portrait of Irena Kanafoska-Dembicka","1912","Witkiewicz");
        }

        if (selected == 2){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.1999f);
            model1.getScaleController().setMaxScale(0.8000f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem2);
            model1.select();

            addDetails(anchorNode,model1,"Sir Isaac Newton portrait","1863","Godfrey Kneller");
        }

        if (selected == 3){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.1999f);
            model1.getScaleController().setMaxScale(0.5000f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem3);
            model1.select();

            addDetails(anchorNode,model1,"Baroque","1929","Zdzislaw Beksinski");
        }

        if (selected == 4){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.1999f);
            model1.getScaleController().setMaxScale(0.5000f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem4);
            model1.select();

            addDetails(anchorNode,model1,"ŚmierćDeath painting","1902","Jacek Malczewski");
        }

        if (selected == 5){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.1999f);
            model1.getScaleController().setMaxScale(0.8000f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem5);
            model1.select();

            addDetails(anchorNode,model1,"Portrait of Adam Mickiewicz","1825","Jan Styka");
        }

        if (selected == 6){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.1999f);
            model1.getScaleController().setMaxScale(0.8000f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem6);
            model1.select();

            addDetails(anchorNode,model1,"Death Crowning Innocence","1896","George Frederic Watts");
        }

        if (selected == 7){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.1999f);
            model1.getScaleController().setMaxScale(0.8000f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem7);
            model1.select();

            addDetails(anchorNode,model1,"Utopian realism","1915","Zdzislaw Beksinski");
        }

        if (selected == 8){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.setParent(anchorNode);
            model1.setRenderable(mitem8);
            model1.select();

            addDetails(anchorNode,model1,"Biblical scene from Nuremberg Chronicle","1493","Malczewski");
        }

        if (selected == 9){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.00009f);
            model1.getScaleController().setMaxScale(0.0050f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem9);
            model1.select();

            addDetails(anchorNode,model1,"Mona Lisa","1503","leonardo da vinci");
        }

        if (selected == 10){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.getScaleController().setMinScale(0.00009f);
            model1.getScaleController().setMaxScale(0.0050f);
            model1.setParent(anchorNode);
            model1.setRenderable(mitem10);
            model1.select();

            addDetails(anchorNode,model1,"General der Infanterie Hermann von François","1933","Hermann Karl Bruno");
        }


        if (selected == 11){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            //model1.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
            model1.setParent(anchorNode);
            model1.setRenderable(mitem11)
                    .animate(true).start();
            model1.select();

            //addDetails(anchorNode,model1,"Lion");
            /*Node titleNode = new Node();
            titleNode.setParent(model1);
            titleNode.setEnabled(false);
            titleNode.setLocalPosition(new Vector3(0.0f, 1.0f, 0.0f));
            titleNode.setRenderable(viewRenderable);
            titleNode.setEnabled(true);*/
        }

    }

    private void addDetails(AnchorNode anchorNode, TransformableNode model, String name,String year,String paintedBy) {

        //ViewRenderable item_number;

        /*ViewRenderable.builder()
                .setView(this,R.layout.model_details)
                .build()
                .thenAccept(renderable -> item_number = renderable);*/

        ViewRenderable.builder().setView(this,R.layout.model_details)
                .build()
                .thenAccept(viewRenderable -> {

                    TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());
                    //nameView.setLocalPosition(new Vector3(0f,model.getLocalPosition().y+0.5f,0));
                    nameView.setLocalPosition(new Vector3(0.0f, 1.0f, 0.0f));
                    nameView.getScaleController().setMinScale(0.1999f);
                    nameView.getScaleController().setMaxScale(0.6000f);
                    nameView.setParent(anchorNode);
                    nameView.setRenderable(viewRenderable);
                    nameView.select();

                    TextView model_title = (TextView) viewRenderable.getView().findViewById(R.id.model_title);
                    model_title.setText(name);

                    TextView model_year = (TextView) viewRenderable.getView().findViewById(R.id.txtYear);
                    model_year.setText(year);

                    TextView model_paintedBy = (TextView) viewRenderable.getView().findViewById(R.id.txtPaintedBy);
                    model_paintedBy.setText(paintedBy);

                    model_title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            anchorNode.setParent(null);
                        }
                    });
                });

    }

    private void setClickListener() {
        for (int i=0;i< arrayView.length;i++)
            arrayView[i].setOnClickListener(this);
    }

    private void setArrayView() {
        arrayView = new View[]{
                imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,
                imageView7,imageView8,imageView9,imageView10,imageView11
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item1){
            selected = 1;
            setBackground(v.getId());
        } else if (v.getId() == R.id.item2) {
            selected = 2;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item3) {
            selected = 3;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item4) {
            selected = 4;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item5) {
            selected = 5;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item6) {
            selected = 6;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item7) {
            selected = 7;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item8) {
            selected = 8;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item9) {
            selected = 9;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item10) {
            selected = 10;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item11) {
            selected = 11;
            setBackground(v.getId());
        }
    }

    private void setBackground(int id) {
        for (int i = 0;i< arrayView.length;i++){
            if (arrayView[i].getId() == id)
                arrayView[i].setBackgroundColor(Color.parseColor("#80333639"));
            else
                arrayView[i].setBackgroundColor(Color.TRANSPARENT);
        }
    }
}