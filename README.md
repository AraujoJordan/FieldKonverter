# FieldKonversor
[![CircleCI](https://circleci.com/gh/AraujoJordan/FieldKonversor.svg?style=shield)](https://circleci.com/gh/AraujoJordan/FieldKonversor)
[![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/AraujoJordan/FieldKonversor/LICENSE)
[![Jitpack Enable](https://jitpack.io/v/AraujoJordan/FieldKonversor.svg)](https://jitpack.io/#AraujoJordan/FieldKonversor/-SNAPSHOT)

Android edit text conversor that can be used for make operations between multiple edit fields. 
This can be easily used as a currency conversor, link inversor, text replacement or any other type of conversion that you want to do with 2 or more fields.

## Installation

#### Step 1. Add the JitPack repository to your project build file 

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### Step 2. Add the dependency to your app build file 

```gradle
dependencies {
        implementation 'com.github.AraujoJordan:FieldKonversor:-SNAPSHOT'
}
```


## Usage

### For any type of conversion

```kotlin
// link inverter
FieldKonversor(editText,editText2) { from, to ->
    from?.text.toString().reversed()
}
```

### For currency conversions
 
 You can use the `CurrencyKonversor` constructor to convert between 2 or many fields

 
```kotlin
CurrencyKonversor(
    CurrencyField(editText1, 0.5, 25000.00),
    CurrencyField(editText2, 2.0, 50000.00)
)
```

The third field of the CurrencyField is actually optional, so you can use something like: `CurrencyField(editText1, 2.0)`
You can even change the place decimal precision using the `CurrencyKonversor` variable `decimalPlaces` integer any time.

