package androidpoint.examples;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
public ProgressBar pb;
    public SwipeRefreshLayout swipeLayout;
    public WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);


        pb = (ProgressBar) findViewById(R.id.pb);
        swipeLayout.setOnRefreshListener(this);

        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        webview.loadUrl("https://www.youtube.com/channel/UCakhVD1UtmS7uVJA14QOxhA/videos");
        webview.setWebViewClient(new WebViewClient());
        webview.setScrollbarFadingEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webview.setWebChromeClient(new WebChromeClient());
        pb.setProgress(0);
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int Progress) {
                pb.setProgress(Progress);
                setTitle("P;ease wait a moment......");
                if (Progress == 100) {
                    setTitle(view.getTitle());
                    pb.setVisibility(view.GONE);

                } else {
                    pb.setVisibility(view.VISIBLE);

                }
                super.onProgressChanged(view, Progress);
            }
        });
    }

    @Override
    public void onRefresh() {
        webview.reload();
        swipeLayout.setRefreshing(false);
    }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    }

