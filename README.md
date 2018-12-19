# HASHRG
HASHRG is a CA Fast Data Masker custom function, created to enable CA FDM to consistently mask [Brazilian General Registry](https://en.wikipedia.org/wiki/Brazilian_identity_card) (in Portuguese Registro Geral, aka RG).


## Main Features
Before We begin, We must clarify some points about Brazilian RG. BR RG do not have a unique algorithm for the entire country, each state can have it's own format. This generates a second issue, because of that the number is not unique around the country, so its totally possible to find different persons, with same RG number, on different states. With that in mind, this Hash function will **ONLY WORK** with RG from states that follows the rules below:

1. RG, without it's format mask (*XX.XXX.XXX-X*), should have a length of 9 characters, 8 numbers and one check digit;
2. Check Digit can be represented by the letter X in some particular cases;
4. Our check digit is calculated based on mod11 algorithm.

RGs which don't follow these particular rules are considered invalid by our mask function, and will not be masked. São Paulo and Rio de Janeiro are two states confirmed to follow these rules above.

So, with everything clarified we can list the main features of the mask function:
* The function will hash Brazilian RG with or without mask.
* The function ensures the same format and consistent masks for the same original value, but does not ensure uniqueness.
* The mask algorithm is based on CA Fast Data Masker FORMATHASH.
* It will maintain nulls and empty strings.

## Getting Started

### Build Prerequisites

These instructions for how to get and compile the source code. The project was created with **Gradle** to build and handle dependencies. All required libs (*minus one, see below*) are handled automatically by Gradle. This project works with **Java 1.8+**.

```
gradle init --type java-library
```

**CA FastDataMasker lib** is also required. It's presented as part of the Fast Data Masker application, which can only by used by those whom has a valid CA FDM license. The lib is not presented at this repository and should be manually added to *libs* folder, after you have push the code into your workstation.

```
Project Root
  --libs
    --FastDataMasker.jar
```

### Installing

A step by step, with examples, that tell you how to get the built function working on a CA Fast Data Masker installation.

First of all, custom functions support were added at **CA Fast Data Masker version 4.3**. So, if you have a version prior to this, stop now and update it to the last release, or this will not work at all.

Go to the FDM install folder and create a folder called `custom`.
```
Usually it's installed at C:\Program Files\Grid-Tools\FastDataMasker
```

Modify the provided the *custom_config.xml* and add the xml below inside the `<functions>` tag

```xml
<function>
  <name>HASHRG</name>
  <description>HASHRG - Consistently mask a given brazilian Identity Number (aka RG),
    retaining the original format of the number</description>
  <parm1></parm1>
  <parm2></parm2>
  <parm3></parm3>
  <parm4></parm4>
  <char>true</char>
  <number>true</number>
  <date>false</date>
  <char_date>false</char_date>
  <custom>true</custom>
  <class_name>com.ca.datamasker.custom.HashRG</class_name>
</function>
```

The function will be available for Number and Character data types. It usually will be placed at the end of the available functions with the name `HASHRG`.


### Examples

Below you can find some examples, with the input CPNJ and how the function will mask it:

| Original RG    | Masked RG    | Obs                                                    |
| -------------- |--------------| -------------------------------------------------------|
| 02.765.061-3   | 29.516.210-7 | The function can Hash RG with its format mask              |
| 021211711      | 290974501    | Masked value without Format Mask                                                  |
| 02.121.171-1   | 29.097.450-1 | Mask function will maintain integrity. Same RG from row above, same masked value  |
| *Null*         | *Null*       | Masking function will maintain Null values             |
| `Empty`        | `Empty`      | Masking function will maintain Empty values            |


## Built With

* [Java](https://www.oracle.com/technetwork/java/index.html) - Main language used to create this function.
* [FastDataMasker](https://docops.ca.com/ca-test-data-manager) - CA proprietary Data Masker library
* [Validator](https://commons.apache.org/proper/commons-validator/) - Validator library used by format hash
* [JUnit](https://junit.org/junit4/) - Testing framework
* [Graddle](https://gradle.org/) - Build and Dependency Management

## Contributing

Please read [CONTRIBUTING.md]([CONTRIBUTING.md]) for details on our code of conduct, and the process for submitting pull requests to me.

## Versioning

I use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/arturfigueira/HASHRG/tags).

## Authors

*  [ArturFigueira](https://github.com/arturfigueira) - *Initial work*

## License

This project is licensed under the GNU GENERAL PUBLIC LICENSE - see the [LICENSE.md](LICENSE.md) file for details.
