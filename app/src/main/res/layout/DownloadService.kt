package layout

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.text.TextUtils
import java.io.IOException

/**
 * Created by logonrm on 17/02/2018.
 */
class DownloadService : IntentService(DownloadService::class.java.name) {
    companion object {
        val STATUS_RUNNING = 0
        val STATUS_FINISHED = 1
        val STATUS_ERROR = 2
    }

    override fun onHandleIntent(intent: Intent?) {
        val receiver = intent!!.getParcelableExtra<ResultReceiver>("receiver")
        val url = intent.getStringExtra("url")
        val bundle = Bundle()
        if (!TextUtils.isEmpty(url)) {
            /*Atualiza a UI: Download Service rodando*/
            receiver.send(STATUS_RUNNING, Bundle.EMPTY)
            try {
                val results = downloadData(url)
                /* Envia o resultado de volta para a activity */
                if (null != results && results.isNotEmpty()) {
                    bundle.putStringArrayList("result", results.toTypedArray())
                    receiver.send(STATUS_FINISHED, bundle)
                }
            } catch (e: Exception) {
                /* Envia mensagem de erro para a activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString())
                receiver.send(STATUS_ERROR, bundle)
            }
        }
        this.stopSelf()
    }
    @Throws(IOException::class, DownloadException::class)
    private fun downloadData(requestUrl: Striung): List<String?> {
        var inputStream: ImputStream?
        var urlConnection: HttpURLConnection?
        val url = URL(requestUrl)
    }
}