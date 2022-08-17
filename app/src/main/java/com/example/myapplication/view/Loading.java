package com.example.myapplication.view;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

/**
 * loading
 */
public class Loading extends Dialog
    implements DialogInterface.OnShowListener, DialogInterface.OnDismissListener {

//  private ImageLoaderView img;
  private Context context;
//  private SimpleDraweeView img;

  public Loading(@NonNull Context context) {
    super(context, R.style.loading);

    setContentView(R.layout.base_loading);
//    img = findViewById(R.id.img);

    this.context = context;
    setOnShowListener(this);
  }

  @Override
  public void onShow(DialogInterface dialog) {
//    ImageLoader.load(getContext(), "res://drawable/" + R.drawable.load, img);
//    ImageLoader.load(getContext(), drawable2Uri(R.drawable.load), img);

//    SimpleDraweeView simpleDraweeView = (SimpleDraweeView)(img.getView());
//    ImageRequest request = ImageRequestBuilder
//            .newBuilderWithSource(Uri.parse(drawable2Uri(R.drawable.ic_launcher)))
//            .build();
//    DraweeController controller = ((PipelineDraweeControllerBuilder)((PipelineDraweeControllerBuilder)((PipelineDraweeControllerBuilder)Fresco
//            .newDraweeControllerBuilder()
//            .setImageRequest(request))
//            .setAutoPlayAnimations(true)
//            .setOldController(simpleDraweeView.getController()))
//            .setControllerListener(new BaseControllerListener()))
//            .build();
//    simpleDraweeView.setController(controller);

//    ImageRequest imageRequest =
//            ImageRequestBuilder.newBuilderWithResourceId(R.drawable.load).build();
//    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//            .setUri(imageRequest.getSourceUri())
//            .setAutoPlayAnimations(true)
//            .build();
//    img.setController(draweeController);
  }

  @Override
  public void onDismiss(DialogInterface dialog) {

  }

  private String drawable2Uri(int resid){
    Resources resource = context.getResources();
    Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
            + "://" + resource.getResourcePackageName(resid)
            + "/" + resource.getResourceTypeName(resid)
            + "/" + resource.getResourceEntryName(resid));

    Log.e("TAG", "> drawable2Uri = " + uri.toString());
    return uri.toString();
  }

}
