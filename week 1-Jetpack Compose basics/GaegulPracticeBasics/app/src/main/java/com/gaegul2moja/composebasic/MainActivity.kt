package com.gaegul2moja.composebasic

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gaegul2moja.composebasic.ui.theme.ComposeBasicPracticeTheme
import androidx.compose.material.icons.filled.*

/**
 * import androidx.compose.ui.* //compose 사용 시, importing class 는 주로 아래 2개임을 참고하자
 * import androidx.activity.compose.*
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicPracticeTheme { //프로젝트 이름에 따라 네이밍이 달라짐
                MyApp()
            }
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}


@Composable
fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
    /**
     * MaterialTheme.typography.h4.copy로 카피하여 일부 속성만 재지정해 사용한다.
     * Modifier.fillMaxWith 는 match parent 의 역할을 수행
     * Modifier.weight(1f) 는 사용가능한 공간을 모두 채운다
     * Column 은 세로로 레이아웃을 구성한다. (Row, Box 등도 존재)
     *
     * surface 의 색을 변경하면 글자색 또한 자동으로 변하게 되는데, 이는,
     * Surface 자체가 보편적으로 개발자가 사용할 색상을 기본으로 반영해 주기 때문이다.
     * 즉, Surface가 primary이므로, 안에 들어간 객체는 onPrimary 가 자동으로 먹히게 된다.
     *
     * 대부분의 compose UI 요소들은 modifier 들을 파라미터로 받을 수 있도록 확장되어있다.
     * Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
     * dp의 표현은 숫자 후 .을 넣어 단위 표기를 해야한다.
     * */
}

@Preview(showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark")
@Composable
fun DefaultPreview() {
    ComposeBasicPracticeTheme {
        MyApp()
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
    //코드의 중복 해결과 재사용을 위해 해당 Composable을 선언
    //LazyColumn은 Recyclerview 와 유사하고, 큰 리스트를 사용 시 성능에 이점이 있다.
    //RecyclerView 처럼 재활용하지는 않고 새로운 composable 들을 스크롤 시에 넣는다. 이는, 비용적으로 기존 방식보다 이득이라고 한다.
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    //화면 전환, 테마 변경, 권한 변경 시 activity 가 변환되며 기존에 내용이 날아가기 때문에 저장해 놓기 위해 사용

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        //click 하면 button 이 false 가 된다 넘긴 clicked listener 는 내부의 button의 onclick으로 지정된다
    } else {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}
