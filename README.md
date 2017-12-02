# Jay

Yet another JSON library made for completely no reason other than boredom.

# Gradle setup

```gradle
repositories {
	maven {
		url 'https://jitpack.io'
	}
}

dependencies {
	compile 'com.github.Shockah:Jay:1.1'
}
```

# Usage

## Creating a JSONObject

`JSONObject` implements `Map<String, Object>`, meaning you can use it the same way you would use such a map.

```java
JSONObject json = new JSONObject();
json.put("firstName", "Michael");
json.put("lastName", "Smith");
json.put("age", 28);
```

There are some checks in place though - any value that can't be stored in JSON will throw an exception.

```java
JSONObject json = new JSONObject();
json.put("random", new Random()); //throws ClassCastException
```

There also exists a handy but unsafe method (checked for correctness at runtime) for quickly creating a `JSONObject` with specific values.

```java
JSONObject json = JSONObject.of(
	"firstName", "Michael",
	"lastName", "Smith",
	"age", 20
);
```

## Creating a JSONList

`JSONList<T>` implements `List<T>`, meaning you can use it the same way you would use such a list.

It also has the same checks in place as `JSONObject`.

```java
JSONList<String> jsonList = new JSONList<>(String.class);
//JSONList<String> jsonList = new JSONList<>(); //this would also work but disables some checks
jsonList.add("pen");
jsonList.add("pineapple");
jsonList.add("apple");
jsonList.add("pen");
```

```java
//JSONList<String> jsonList = JSONList.of( //this would also work but disables some checks
JSONList<String> jsonList = JSONList.of(String.class,
	"pen", "pineapple", "apple", "pen"
);
```

## Retrieving non-null values

The `getX(String key)` method family on `JSONObject` makes sure a result is always being returned. If the key does not exist, an exception will be cast.

```java
System.out.println(json.getString("toPrint"));
```

```java
int result = json.getInt("a") + json.getInt("b");
```

## Retrieving nullable values

There are multiple ways of handling nullable values.

### getX(String key, X defaultValue)

The `getX(String key, X defaultValue)` method family return either the value or the passed default value.

```java
int entriesPerPage = json.getInt("entriesPerPage", 10);
```

### getOptionalX(String key)

The `getOptionalX(String key)` method family return either the value or `null`. The return type is always a reference type (meaning for primitives it is the boxed type of that primitive - for example `Integer` for `int`).

```java
Long optional = json.getOptionalLong("timestamp");
```

### onX(String key, Action1<X> function)

The `onX(String key, Action1<X> function)` method family do not return anything, but if a value exists, it will call the passed in `Action1<X> function` FunctionalInterface.

```java
json.onString("toPrint", toPrint -> {
	System.out.println(toPrint);
});
```

### onX(String key, Action1<X> function, Action0 orElse)

The `onX(String key, Action1<X> function, Action0 orElse)` method family do not return anything. If a value exists, the passed `Action1<X> function` FunctionalInterface will be called; otherwise the passed `Action0 orElse` will.

```java
json.onString("toPrint", toPrint -> {
	System.out.println(toPrint);
}, () -> {
	System.out.println("No `toPrint` key found.");
});
```

## Additional JSONObject retrieval methods

Other than `getObject`, `getOptionalObject` and `onObject`, there are also some extra ways of retrieving a `JSONObject` value.

The same method variations are also available for `JSONList<T>`.

### getObjectOrEmpty(String key)

The method will return an already existing `JSONObject` or a new, empty one.

```java
System.out.println(json.getObjectOrEmpty("settings").getString("toPrint", "Default print line."));
```

### getObjectOrNew(String key)

This method does the same thing as `getObjectOrEmpty(String key)`, but additionally it also puts the new `JSONObject` into the base object under specified key.

```java
JSONObject jsonSettings = json.getObjectOrEmpty("settings");
jsonSettings.put("entriesPerPage", 15);
System.out.println("" + json.getObject("settings").getInt("entriesPerPage"));
```

## Storing values

The most basic usage is through the `put(Object value)` method from the `Map` interface.

```java
json.put("myKey", "myValue");
```

`putDefault(String key, Object value)` allows to add a value to the JSON only if the key doesn't exist yet.

```java
json.putDefault("asdf", 123);
json.putDefault("asdf", 456);
System.out.println(json.getInt("asdf")); // will print 123
```

### putNewObject(String key)

This allows to put a new `JSONObject` quickly into a JSON.

A similar method is also available for `JSONList<T>`.

```java
JSONObject settingsJson = json.putNewObject("settings");
```

## Comments

If you are writing a new JSON, you have the ability to add comments for each key of the JSON. These comments will be written out to the resulting JSON file by the `JSONPrettyPrinter`. Reading comments from a JSON is not possible.

```java
json.setComment("key", "Some comment.");
System.out.println(json.getComment("key"));
json.clearComments();
```

## Parsing

```java
JSONObject json = new JSONParser().parseObject("{\"key\": \"value\"}");
JSONList<Integer> json = new JSONParser().parseList("[1, 2, 3, 5, 8, 13, 21]").ofInts();
```

## Exporting to JSON

```java
System.out.println(new JSONPrinter().toString(json));
System.out.println(new JSONPrettyPrinter().toString(json));
```