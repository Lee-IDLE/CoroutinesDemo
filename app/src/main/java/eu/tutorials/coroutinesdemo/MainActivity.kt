package eu.tutorials.coroutinesdemo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var customProgressDialog: Dialog? = null
    private var btnExecute: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnExecute = findViewById(R.id.btnExecute)
        btnExecute?.setOnClickListener{
            showProgressDialog()
            // 2.
            // 실행범위 스코프를 지정하고 launch로 suspend 함수나 코루틴 기능을 실행한다.
            lifecycleScope.launch{
                execute("Task executed successfully")
            }

        }
    }

    // 1.
    // 함수를 코루틴으로 실행하려면 함수에 suspend 키워드를 추가하고
    // 실행할 context를 지정한다(withContext).
    // 어떤 스레드에서 무슨 옵션을 실행할지 지정해야 한다.(Dispatchers.IO)
    private suspend fun execute(result: String){

        withContext(Dispatchers.IO){
            for(i in 1..100000){
                Log.e("delay : ", "${i}")
            }

            runOnUiThread{
                cancelProgressDialog()
                Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        customProgressDialog?.show()
    }

    private fun cancelProgressDialog(){
        customProgressDialog?.dismiss()
        customProgressDialog = null
    }
}