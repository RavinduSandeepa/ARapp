package com.rv.sceneform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
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

public class SceneForm extends AppCompatActivity implements FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener { //View.OnClickListener

/*    private ArFragment arFragment;
    private Renderable model;
    private ModelRenderable item1, item2, item3, item4, item5,item6,item7,
                            item8,item9,item10,item11,item12,item13;

    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,
                imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,
                imageView13;

    View arrayView[];
    ViewRenderable name_item;
    int selected = 1;*/

    private ArFragment arFragment;
    private Renderable model;
    private ViewRenderable viewRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceneform);
        getSupportFragmentManager().addFragmentOnAttachListener(this);

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
        }

        loadModels();

/*        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

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
        imageView12 = (ImageView) findViewById(R.id.item12);
        imageView13 = (ImageView) findViewById(R.id.item13);

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
        });*/
    }

    private void loadModels() {
        WeakReference<SceneForm> weakActivity = new WeakReference<>(this);
        ModelRenderable.builder()
                .setSource(this, Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(model -> {
                    SceneForm activity = weakActivity.get();
                    if (activity != null) {
                        activity.model = model;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(
                            this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
        ViewRenderable.builder()
                .setView(this, R.layout.view_model_title)
                .build()
                .thenAccept(viewRenderable -> {
                    SceneForm activity = weakActivity.get();
                    if (activity != null) {
                        activity.viewRenderable = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
            arFragment.setOnViewCreatedListener(this);
            arFragment.setOnTapArPlaneListener(this);
        }
    }

    @Override
    public void onViewCreated(ArSceneView arSceneView) {
        arFragment.setOnViewCreatedListener(null);

        // Fine adjust the maximum frame rate
        arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }
    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (model == null || viewRenderable == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the Anchor.
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create the transformable model and add it to the anchor.
        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
        model.setParent(anchorNode);
        model.setRenderable(this.model)
                .animate(true).start();
        model.select();

        Node titleNode = new Node();
        titleNode.setParent(model);
        titleNode.setEnabled(false);
        titleNode.setLocalPosition(new Vector3(0.0f, 1.0f, 0.0f));
        titleNode.setRenderable(viewRenderable);
        titleNode.setEnabled(true);
    }


/*    private void setupModel() {
        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item1 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model1", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item2 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model2", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item3 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model3", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item4 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model4", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item5 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model5", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item6 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model6", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item7 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model7", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item8 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model8", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item9 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model9", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item10 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model10", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item11 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model11", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item12 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model12", Toast.LENGTH_LONG).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this,Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(renderable -> item13 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model13", Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if (selected == 1){
            TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
            model1.setParent(anchorNode);
            model1.setRenderable(item1);
            model1.select();
        }
    }

    private void setClickListener() {
        for (int i=0;i< arrayView.length;i++)
            arrayView[i].setOnClickListener(this);
    }

    private void setArrayView() {
        arrayView = new View[]{
                imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,
                imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,
                imageView13
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
        else if (v.getId() == R.id.item12) {
            selected = 12;
            setBackground(v.getId());
        }
        else if (v.getId() == R.id.item13) {
            selected = 13;
            setBackground(v.getId());
        }
    }

    private void setBackground(int id) {
        for (int i = 0;i< arrayView.length;i++){
            if (arrayView[i].getId() == id)
                arrayView[i].setBackgroundColor(Color.parseColor("80333639"));
            else
                arrayView[i].setBackgroundColor(Color.TRANSPARENT);
        }
    }*/
}