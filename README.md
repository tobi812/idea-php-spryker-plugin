Spryker PhpStorm Plugin
========================

![Build](https://github.com/tobi812/idea-php-spryker-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/8490-spryker-plugin.svg)](https://plugins.jetbrains.com/plugin/8490-spryker-plugin)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/8490-spryker-plugin.svg)](https://plugins.jetbrains.com/plugin/8490-spryker-plugin)

<!-- Plugin description -->

The Spryker Plugin was built to improve the developer experience with the Spryker.

<!-- Plugin description end -->

## Installation

- Using PhpStorms built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "idea-php-spryker-plugin"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/tobi812/idea-php-spryker-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


Features
------------------------


## 1. Generate DocBlocks automatically

Some of the Spryker base classes, have very special DocBlocks. In order to generate them with the help of the Plugin, browse to the class, press `alt` + `enter` and select `Update Spryker DocBlock`.


![Generate DocBlock](https://raw.githubusercontent.com/tobi812/idea-php-spryker-plugin/master/docs/update_docblock.gif)

## 2. Generate Spryker classes

It also possible to generate base classes of Spryker automatically.

- right-click the directory in the project structure
- select `New` and (if possible) the file you want to create will appear in the menu

![Generate DocBlock](https://raw.githubusercontent.com/tobi812/idea-php-spryker-plugin/master/docs/create_spryker_file.gif)

## 3. Bundle generation

To create a new bundle:

- right-click the app directory (Yves, Zed or Client)
- select `New and Create (Yves|Zed|Client) Bundle`

![Generate DocBlock](https://raw.githubusercontent.com/tobi812/idea-php-spryker-plugin/master/docs/create_spryker_bundle.gif)

## 4. Navigate to corresponding factory method

Next to the definition of a module class the Spryker logo will appear, which will navigate you to the corresponding factory 
method.

## 5. Generate factory methods automatically

Browse to any Spryker factory select the class name and press `alt` + `enter` and `Add Spryker missing Factory methods` to automatically generate missing factory methods.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
