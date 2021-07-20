package mj.project.shopping.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

//dp->픽셀 , 이미지뷰
private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

internal fun ImageView.clear() = Glide.with(context).clear(this)

internal fun ImageView.loadCenterCrop(url: String, corner: Float = 0f) { // CenterCrop은 가로세로중 짧은쪽의 길이를 레이아웃에 꽉차게 크기를 맞춰출력함
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply {
            if (corner > 0) transforms(CenterCrop(), RoundedCorners(corner.fromDpToPx()))
        }
        .into(this)
}

internal fun ImageView.load(url: String, corner: Float = 0f) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL) // 모든 이미지 캐싱, Glide는 기본적으로 메모리 & 디스크에 이미지를 캐싱하여 불필요한 네트워크 연결을 줄인다.
        .apply {
            if (corner > 0) transforms(RoundedCorners(corner.fromDpToPx())) //dp->픽셀
        }
        .into(this) // 이미지를 보여줄 View를 지정한다.
}