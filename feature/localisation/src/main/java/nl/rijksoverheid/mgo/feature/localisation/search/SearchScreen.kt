package nl.rijksoverheid.mgo.feature.localisation.search

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButton
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionBorder
import nl.rijksoverheid.mgo.component.theme.bodyText
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager
import nl.rijksoverheid.mgo.component.theme.R as ThemeR

@Composable
fun SearchScreen() {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val viewState: SearchScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    SearchScreenContent(
        viewState = viewState,
        onSetName = { name ->
            viewModel.setName(name)
        },
        onSetCity = { city ->
            viewModel.setCity(city)
        },
        onSearch = {
            viewModel.getSearchResults()
        },
    )
}

@Composable
private fun SearchScreenContent(
    viewState: SearchScreenViewState,
    onSetName: (name: String) -> Unit,
    onSetCity: (city: String) -> Unit,
    onSearch: () -> Unit,
) {
    val navigationManager: NavigationManager = LocalNavigationManager.current
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { navigationManager.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.general_previous),
                        )
                    }
                },
            )
        },
        backgroundColor = Color.Transparent,
        content = { innerPadding ->
            ColumnWithButton(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp),
                buttonText = stringResource(id = R.string.general_search),
                onButtonClick = onSearch,
            ) {
                Text(
                    text = stringResource(id = R.string.localisation_search_title),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.localisation_search_name_hint),
                    style =
                        MaterialTheme
                            .typography.bodyText,
                )

                BasicTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    value = viewState.city,
                    onValueChange = onSetCity,
                    textStyle = MaterialTheme.typography.bodyText,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier =
                                Modifier
                                    .heightIn(40.dp)
                                    .border(
                                        BorderStroke(1.dp, SolidColor(MaterialTheme.colors.actionBorder())),
                                        shape = RoundedCornerShape(8.dp),
                                    )
                                    .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            content = { innerTextField() },
                        )
                    },
                )

                if (viewState.nameError != null) {
                    TextFieldError(modifier = Modifier.padding(top = 8.dp), text = viewState.nameError)
                }

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.localisation_search_city_hint),
                    style =
                        MaterialTheme
                            .typography.bodyText,
                )

                BasicTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    value = viewState.city,
                    onValueChange = onSetName,
                    textStyle = MaterialTheme.typography.bodyText,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier =
                                Modifier
                                    .heightIn(40.dp)
                                    .border(
                                        BorderStroke(1.dp, SolidColor(MaterialTheme.colors.actionBorder())),
                                        shape = RoundedCornerShape(8.dp),
                                    )
                                    .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            content = { innerTextField() },
                        )
                    },
                )

                if (viewState.cityError != null) {
                    TextFieldError(modifier = Modifier.padding(top = 8.dp), text = viewState.cityError)
                }
            }
        },
    )
}

@Composable
private fun TextFieldError(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.error) {
        Row(modifier = modifier) {
            Icon(painter = painterResource(id = ThemeR.drawable.ic_input_error), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = stringResource(id = text),
                style = MaterialTheme.typography.bodyText,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun NameAndCity() {
    MgoTheme {
        SearchScreenContent(
            viewState = SearchScreenViewState(name = "Tandarts", city = "Rotterdam", nameError = null, cityError = null),
            onSetName = {},
            onSetCity = {},
            onSearch = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun EmptyError() {
    MgoTheme {
        SearchScreenContent(
            viewState =
                SearchScreenViewState(
                    name = "",
                    city = "",
                    nameError = R.string.localisation_search_name_error,
                    cityError =
                        R
                            .string.localisation_search_city_error,
                ),
            onSetName = {},
            onSetCity = {},
            onSearch = {},
        )
    }
}
