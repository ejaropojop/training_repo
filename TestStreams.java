package org.ssglobal.training.codes.streams;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class TestStreams {

	public static void main(String[] args) {

		
		//Primitive Data
		
		//Creation of Stream
		
		//Integers
		IntStream intstream = IntStream.empty();
		IntStream instream2 = IntStream.of(10);
		
		IntPredicate filterEqualTo10 =  (value) -> {
			if (value == 10) {
				return true;
			}
			return false;
		};
		
		IntUnaryOperator mapAdd50 = (t) -> {
			return t + 50;
		};
		
		IntConsumer printValue = (t) -> {
			System.out.println(t);
		};
		
		IntToDoubleFunction mapIntToDouble = (t) -> {
			return t * 50.0;
		};
		
		//Intermediate Operators
		//Functional Programming
		double finalValue = 
				instream2.filter(filterEqualTo10).map(mapAdd50)
				.mapToDouble(mapIntToDouble).sum();
		System.out.println(finalValue);
		
		

		IntStream arrayStream = IntStream.of(1, 2, 3, 4, 10, 56, 4, 7);
		
		IntPredicate filterGreaterThan55 = (value) -> {
			if (value > 55) {
				return  true;
			}
			return false;
		};
		
		//distinct() remove repetitions in array | merge duplicates
		int[] arrayVal = arrayStream.distinct().map(mapAdd50).filter(filterGreaterThan55)
				.toArray(); 
		
		for (int lookup: arrayVal) {
			System.out.println(lookup);
		}
		
		/*
		double avgVal = arrayStream.distinct().map(mapAdd50).filter(filterGreaterThan55)
				.average().orElse(0);
		System.out.println(avgVal);
		*/
		
		IntSupplier supplyRandomInt = () -> {
			
			int num = (int) (Math.random() * 100);
			return num;
			
		};
		
		IntStream rangeIntStream = IntStream.range(20, 50);
		IntStream genIntStream = IntStream.generate(supplyRandomInt);
		
		//Long
		LongStream arrayLongStream = LongStream.of(100L, 20L, 67L, 11L, 45L);
		LongStream rangeLongStream = LongStream.range(100L, 500L);
		
		LongSupplier supplyRandomLong = () -> {
			
			long num = (long) (Math.random() * 100);
			return num;
			
		};
		LongStream genLongStream = LongStream.generate(supplyRandomLong);
		
		//Double
		DoubleStream dblStream = DoubleStream.of(10.5, 20.5, 67.5, 11.5, 45.5);
		
		DoubleSupplier supplyRandomDbl = () -> {
			
			double num = (double) (Math.random() * 100);
			return num;
			
		};
		DoubleStream rangeDblStream = DoubleStream.generate(supplyRandomDbl);
		
		
		//String supplier
		Supplier<String> supplyStringData = () -> {

			byte[] msgBuffer = new byte[10];
			new Random().nextBytes(msgBuffer);
			String randomString = new String(msgBuffer, Charset.forName("TF_8"));
			
			return randomString;
		};
		
		
		//Objects
		Stream<String> strStream = Stream.generate(supplyStringData);
		Stream<String> nameStream = Stream.of("Anna", "Lorna", "Fe", "Mark", "Peter");
		
		
		Profile p1 = new Profile("Anna", "Luna", 45, 90000.00F);
		Profile p2 = new Profile("Juan", "De Dios", 45, 20000.00F);
		Profile p3 = new Profile("Jose", "Rizal", 30, 90000.00F);
		
		Stream<Profile> profileStream = Stream.of(p1, p2, p3);
		
		List<String> names =
				new ArrayList<>(Arrays.asList("Anna", "Lorna", "Fe", "Mark", "Peter"));
		Stream<String> nameStream2 = names.stream();
		
		List<Profile> profiles = new ArrayList<>(Arrays.asList(p1, p2, p3));
		Stream<Profile> profileStream2 = profiles.stream();
		
		//To extract all the Profiles with age greater than 30 return a new collection
		Predicate<Profile> filterAgeGreater30 = (p) -> {
			
			if (p.getAge() > 30) {
				return true;
			}
			return false;
		};
				
		Comparator<Profile> sortedDescLastName = (o1, o2) -> {
			
			if (o1.getLastname().compareTo(o2.getLastname()) == 0) {
				return 0;
			} else if (o1.getLastname().compareTo(o2.getLastname()) > 0) {
				return -2;
			} else {
				return 2;
			}
		};
		
		List<Profile> profiles2 = profileStream2.filter(filterAgeGreater30).sorted(sortedDescLastName)
				.collect(Collectors.toList());
		
		System.out.println(profiles2);
		
		//Retrieve all the name then store that in a list, sorted by descending firstname
		
		Comparator<Profile> sortedDescFirstname = (o1, o2) -> {
			
			if (o1.getFirstname().compareTo(o2.getFirstname()) == 0) {
				return 0;
			} else if (o1.getFirstname().compareTo(o2.getFirstname()) > 0) {
				return -2;
			} else {
				return 2;
			}
		};
		
		Function<Profile, String> mapProfileName = (p) -> {
			return String.join(" ", p.getFirstname(), p.getLastname());
		};
		
		profileStream2 = profiles.stream();
		List<String> nameStr = profileStream2.sorted(sortedDescFirstname)
				.map(mapProfileName).collect(Collectors.toList());
		System.out.println(nameStr);
		
		
		//Get the total age and average
		profileStream2 = profiles.stream();

		Function<Profile, Integer> mapProfileToAge = (p) -> {
			return p.getAge();
		};
		int totalAge = profileStream2.map(mapProfileToAge)
				.mapToInt(Integer::intValue).sum();
		profileStream2 = profiles.stream();
		double avgAge = profileStream2.map(mapProfileToAge)
				.mapToInt(Integer::intValue).average().orElse(0);
		System.out.format("%d %f", totalAge, avgAge);
		
		
		//Searching for Anna
		Function<Profile, String> mapProfileFirstName = (p) -> {
			return p.getFirstname();
		};
		
		Predicate<String> filterAnnaName = (p) -> {
			
			if (p.equalsIgnoreCase("Anna")) {
				return true;
			}
			return false;
		};

		profileStream2 = profiles.stream();
		boolean isThereAnna = profileStream2.map(mapProfileFirstName)
				.anyMatch(filterAnnaName);
		System.out.println(isThereAnna);
		
	}

}


class Profile {
	
	private String firstname;
	private String lastname;
	private Integer age;
	private Float salary;
	
	public Profile() {
	}
	
	public Profile(String firstname, String lastname, Integer age, Float salary) {
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.salary = salary;
		
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}
	
	
	@Override
	public String toString() {
		return String.join(" ",firstname, lastname, String.valueOf(age), String.valueOf(salary));
	}
}
