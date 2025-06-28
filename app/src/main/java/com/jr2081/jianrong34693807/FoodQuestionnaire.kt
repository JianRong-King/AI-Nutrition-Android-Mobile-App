package com.jr2081.jianrong34693807

import android.app.TimePickerDialog
import android.content.Intent
import androidx.compose.material3.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jr2081.jianrong34693807.data.AuthManager
import com.jr2081.jianrong34693807.data.viewModels.QuestionnaireViewModel
import com.jr2081.jianrong34693807.ui.theme.JianRong34693807Theme
import java.util.Calendar

data class Persona(val title: String, val content: String, val imageId: Int)
val personas = listOf(
    Persona(
        title = "Health Devotee",
        content = "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.",
        imageId = R.drawable.persona_1
    ),
    Persona(
        title = "Mindful Eater",
        content = "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",
        imageId = R.drawable.persona_2
    ),
    Persona(
        title = "Wellness Striver",
        content = "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.",
        imageId = R.drawable.persona_3
    ),
    Persona(
        title = "Balance Seeker",
        content = "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
        imageId = R.drawable.persona_4
    ),
    Persona(
        title = "Health Procrastinator",
        content = "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
        imageId = R.drawable.persona_5
    ),
    Persona(
        title = "Food Carefree",
        content = "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
        imageId = R.drawable.persona_6
    )


)






class FoodQuestionnaire : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            JianRong34693807Theme {
                val context = LocalContext.current
                val questionnaireViewModel: QuestionnaireViewModel = viewModel(
                    factory = QuestionnaireViewModel.QuestionnaireViewModelFactory(context)
                )


                val userId = AuthManager.getUserId()?.toInt()
                if (userId != null) {
                    // Load the current user data from the database
                    questionnaireViewModel.loadCurrentUserData(userId)
                    Toast.makeText(context, "Loaded from Db!", Toast.LENGTH_SHORT).show()
                }

                Scaffold(
                    topBar = { TopAppBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding).padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {




                        // Pass checkbox states and update values accordingly
                        FoodCheckboxes(
                            questionnaireViewModel.isFruitsChecked(), questionnaireViewModel::setFruitsChecked,   //  :: is short form for this { value -> questionnaireViewModel.setFruitsChecked(value) }
                            questionnaireViewModel.isVegetablesChecked(), questionnaireViewModel::setVegetablesChecked,
                            questionnaireViewModel.isGrainsChecked(), questionnaireViewModel::setGrainsChecked,
                            questionnaireViewModel.isRedMeatChecked(), questionnaireViewModel::setRedMeatChecked,
                            questionnaireViewModel.isSeafoodChecked(), questionnaireViewModel::setSeafoodChecked,
                            questionnaireViewModel.isPoultryChecked(), questionnaireViewModel::setPoultryChecked,
                            questionnaireViewModel.isFishChecked(), questionnaireViewModel::setFishChecked,
                            questionnaireViewModel.isEggsChecked(), questionnaireViewModel::setEggsChecked,
                            questionnaireViewModel.isNutsSeedsChecked(), questionnaireViewModel::setNutsSeedsChecked
                        )



                        Spacer(modifier = Modifier.height(14.dp))

                        PersonaTitleLayout()

                        Spacer(modifier = Modifier.height(14.dp))


                        // Function that handle the modal pop up and modal info
                        AllButtonModals(personas)

                        Spacer(modifier = Modifier.height(14.dp))


                        // Function that handle the Persona DropdownMenu
                        PersonaDropdownMenu(questionnaireViewModel)


                        Spacer(modifier = Modifier.height(16.dp))

                        // Function for All timer question
                        TimingsQuestion(questionnaireViewModel.biggestMealTime, questionnaireViewModel.sleepTime, questionnaireViewModel.wakeUpTime)
                        Spacer(modifier = Modifier.height(10.dp))

                        // Function that saves food information to database
                        SaveButton(questionnaireViewModel)

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val context = LocalContext.current

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text("Food Intake Questionnaire")
        },
        navigationIcon = {
            IconButton(onClick = {
                //// Set Auth manager as log out
                AuthManager.logout(context)
                context.startActivity(Intent(context, AuthPage::class.java)) })
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }


        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
fun FoodCheckboxes(
    checkFruits: Boolean, onCheckFruits: (Boolean) -> Unit,
    checkVegetables: Boolean, onCheckVegetables: (Boolean) -> Unit,
    checkGrains: Boolean, onCheckGrains: (Boolean) -> Unit,
    checkRedMeat: Boolean, onCheckRedMeat: (Boolean) -> Unit,
    checkSeafood: Boolean, onCheckSeafood: (Boolean) -> Unit,
    checkPoultry: Boolean, onCheckPoultry: (Boolean) -> Unit,
    checkFish: Boolean, onCheckFish: (Boolean) -> Unit,
    checkEggs: Boolean, onCheckEggs: (Boolean) -> Unit,
    checkNutsSeeds: Boolean, onCheckNutsSeeds: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier.height(44.dp))

        Text(
            text = "Tick all the food categories you can eat",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            FoodCheckBox("Fruits", checkFruits, onCheckFruits)
            FoodCheckBox("Vegetables", checkVegetables, onCheckVegetables)
            FoodCheckBox("Grains", checkGrains, onCheckGrains)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            FoodCheckBox("Red Meat", checkRedMeat, onCheckRedMeat)
            FoodCheckBox("Seafood", checkSeafood, onCheckSeafood)
            FoodCheckBox("Poultry", checkPoultry, onCheckPoultry)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            FoodCheckBox("Fish", checkFish, onCheckFish)
            FoodCheckBox("Eggs", checkEggs, onCheckEggs)
            FoodCheckBox("Nuts/Seeds", checkNutsSeeds, onCheckNutsSeeds)
        }
    }
}


//  Reusable Checkbox Component
@Composable
fun FoodCheckBox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {


    Row(
        modifier = Modifier.width(125.dp).fillMaxWidth().padding(0.dp), // Fixed width for consistency
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,

        ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
        Text(
            text = text,
            fontSize = 14.sp,

            )
    }
}


@Composable
fun PersonaTitleLayout() {
    Column (
        modifier = Modifier.fillMaxWidth().padding(0.dp),
    ) {
        Text(
            text = "Your Persona",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Text(
            text = "People can broadly classified into 6 different types based on\n"
                    + "their eating preferences. Click on each button below to find out different types, and select the type that best fits you!",
            fontSize = 12.sp
        )

    }

}



@Composable
fun AllButtonModals(personas: List<Persona>) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ShowButtonAndModal(personas[0])
        ShowButtonAndModal(personas[1])
        ShowButtonAndModal(personas[2])
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ShowButtonAndModal(personas[3])
        ShowButtonAndModal(personas[4])
        ShowButtonAndModal(personas[5])
    }
}


@Composable
fun ShowButtonAndModal(personas : Persona) {

    var showModal by remember { mutableStateOf(false) }

    Button(onClick = { showModal = true },
        modifier = Modifier.padding(0.dp),
        contentPadding = PaddingValues(10.dp) ) {
        Text(personas.title, fontSize = 12.sp)
    }

    if (showModal) {
        AlertDialog(
            onDismissRequest = { showModal = false },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Image(
                        painter = painterResource(id = personas.imageId),
                        contentDescription = "Persona Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(120.dp)
                    )

                    Text(
                        text = personas.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        text = personas.content,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),  // Padding for better readability
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),  // Make Row span the full width
                    horizontalArrangement = Arrangement.Center  // Center the Button horizontally
                ) {
                    Button(onClick = { showModal = false }) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaDropdownMenu(
    questionnaireViewModel: QuestionnaireViewModel
) {
    var expanded by remember { mutableStateOf(false) } // Controls if the dropdown is visible

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Which persona best fits you?", fontWeight = FontWeight.Bold)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }, // Toggle dropdown
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = questionnaireViewModel.selectedPersona.ifEmpty { "Select option" },// Display selected persona title
                onValueChange = {},
                readOnly = true, // Prevent manual input
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth().height(56.dp)
            )



            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                personas.forEach { persona ->
                    DropdownMenuItem(
                        text = { Text(persona.title) },
                        onClick = {
                            questionnaireViewModel.addSelectedPersona(persona.title) // Lift state up
                            expanded = false // Close the menu
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun TimingsQuestion(bigMealTime :MutableState<String> , sleepTime : MutableState<String>, wakeUpTime : MutableState<String> ) {

    Text(text = "Timings", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)

    Column (modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(6.dp))
        TimingQuestion("What time of day approx. do you normally eat your biggest meal?", bigMealTime )
        Spacer(modifier = Modifier.height(10.dp))
        TimingQuestion("What Time of day approx. do you go to sleep at night?", sleepTime)
        Spacer(modifier = Modifier.height(10.dp))
        TimingQuestion("What time of day approx. do you wake up in the morning?", wakeUpTime)
    }
}

@Composable
fun TimingQuestion(timingQuestion:String, questionTime: MutableState<String>) {
    Row() {
        Text(text = timingQuestion,
            fontSize = 14.sp,
            modifier = Modifier.width(240.dp).padding(end=10.dp))

        val mTimePickerDialog = timePickerFun(questionTime)

        Button(modifier = Modifier.width(180.dp) , onClick = {mTimePickerDialog.show()}) {

            Image(
                painter = painterResource(id = R.drawable.clockicon),
                contentDescription = "Clock Icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = " ${questionTime.value}")
        }
    }
}

@Composable
fun timePickerFun(mTime: MutableState<String>) : TimePickerDialog {

    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    mCalendar.time = Calendar.getInstance().time

    return TimePickerDialog(
        mContext, {_, mHour:Int, mMinute:Int -> mTime.value = "$mHour:$mMinute"}, mHour, mMinute,false
    )
}



@Composable
fun SaveButton(
    questionnaireViewModel: QuestionnaireViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            modifier = Modifier.width(200.dp),
            onClick = {
                if (questionnaireViewModel.selectedPersona == "Select option") {
                    Toast.makeText(context, "Please Select A Persona to Proceed", Toast.LENGTH_LONG).show()
                } else {
                    val userId = AuthManager.getUserId()?.toInt()
                    if (userId != null) {
                        questionnaireViewModel.saveCurrentUserData(userId)
                        Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Not Saved!", Toast.LENGTH_SHORT).show()
                    }

                    context.startActivity(Intent(context, Dashboard::class.java))
                }
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.saveicon),
                contentDescription = "Save Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Save")
        }

    }
}
