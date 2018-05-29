package my.code.trykotlin.business

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.ResultPoint
import my.code.trykotlin.R

import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_scan_barcode.*
import kotlinx.android.synthetic.main.custom_barcode_scanner.*

/**
 * 摄像头扫码页面
 *
 */
class ScanBarcodeActivity : AppCompatActivity(), BarcodeCallback, DecoratedBarcodeView.TorchListener {

    private var disposable: Disposable? = null
    private lateinit var capture: CaptureManager
    private var scanEnable: Boolean = true

    override fun barcodeResult(result: BarcodeResult?) {


        if (scanEnable) {
            startActivityForResult(Intent(this, LoadingProcessRobotInfoActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(IntentKeynames.KEY_ROBOT_CODE, result.toString()), 777)
            scanEnable = false
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 777 && resultCode == Activity.RESULT_CANCELED) {
            finish()
        }
    }

    override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_scan_barcode)
        addThis(this)

        capture = CaptureManager(this, qr_view)
        capture.decode()
        qr_view.decodeContinuous(this)
        qr_view.setTorchListener(this)

        ll_switch_flashlight.setOnClickListener {
            when (tv_torch_state.text) {
                getString(my.code.trykotlin.R.string.torch_on) -> qr_view.setTorchOn()
                getString(my.code.trykotlin.R.string.torch_off) -> qr_view.setTorchOff()
            }
        }

        btn_input_by_hand.setOnClickListener {

            val intent = Intent(this, InputCodeByHandActivity::class.java)
            startActivity(intent)

        }


        rl_scan_back.setOnClickListener {
            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        capture.onResume()
        scanEnable = true
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onTorchOn() {
        tv_torch_state.text = getString(my.code.trykotlin.R.string.torch_off)
    }

    override fun onTorchOff() {
        tv_torch_state.text = getString(my.code.trykotlin.R.string.torch_on)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
