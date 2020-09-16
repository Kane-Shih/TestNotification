package tw.kaneshih.testnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val notificationLauncher = NotificationLauncher(this)
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val log = findViewById<TextView>(R.id.log)
        log.movementMethod = ScrollingMovementMethod()
        findViewById<View>(R.id.buttonPost).setOnClickListener {
            val selectedGroup = radioGroup.getSelectedGroupId()
            val title = "title:$count | group:$selectedGroup"
            notificationLauncher.post(
                title = title,
                description = "whatever description",
                groupId = selectedGroup
            )
            count++

            log.append(title + "\n")

        }
    }

    private fun RadioGroup.getSelectedGroupId(): String {
        return findViewById<RadioButton>(this.checkedRadioButtonId).text.toString()
    }

}