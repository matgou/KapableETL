# Example Population Francaise

An example to graph the french population evolution from INSEE data: Population_France.csv.

## Usage

You can try this example with the command :
 
```
    java -jar KapableETL-1.0-SNAPSHOT.jar example/Population_France/PopuationFrance_context.xml
```
    
This command produce the Population_France.jpg file with the graph of CSV data

## Explanation of configuration PopulationFrance_context.xml

Population_France.csv is a file contain 3 column : year, month, value. Whe would like graph value in Y-Axis and date in X-Axis. So whe need to build a new column : date fron month and year, the Addition operation make this work.

### Step 1 : Model Definition 

Wee need to define the for column : year, month, value and date, with the xml bellow. Colums Year, Month, date are DateTimeDimension, and column value is just Dimension with Integer property. 

```
   <!-- #################################################### -->
   <!-- Data Model -->
   <!-- #################################################### -->
   
   <!-- Year column -->
   <bean id="yearFormater" class="java.text.SimpleDateFormat">
       <constructor-arg value="yyyy"/>
   </bean>
   <bean id="columnYear" class="info.kapable.tools.pojo.DateTimeDimention">
       <constructor-arg value="0"/>
       <constructor-arg ref="yearFormater"/>
   </bean>

   <!-- Month column -->
   <bean id="monthFormater" class="java.text.SimpleDateFormat">
       <constructor-arg value="MM"/>
   </bean>
   <bean id="columnMonth" class="info.kapable.tools.pojo.DateTimeDimention">
       <constructor-arg value="1"/>
       <constructor-arg ref="monthFormater"/>
   </bean>

   <!-- Date column -->
   <bean id="dateFormater" class="java.text.SimpleDateFormat">
       <constructor-arg value="MM/yyyy"/>
   </bean>
   <bean id="columnDate" class="info.kapable.tools.pojo.DateTimeDimention">
       <constructor-arg value="3"/>
       <constructor-arg ref="dateFormater"/>
   </bean>

   <!-- Value column -->
   <bean id="columnValue" class="info.kapable.tools.pojo.Dimention">
       <constructor-arg value="2"/>
       <constructor-arg value="java.lang.Integer" />
   </bean>
```

From this columns now, whe can build a mapping to attribute each csv column to defined dimension :

```
   <bean id="dataInputMapping" class="info.kapable.tools.MappingModel.IndexedMapModel">
	<property name="column">
		<list>
			<ref bean="columnYear" />
			<ref bean="columnMonth" />
			<ref bean="columnValue" />
		</list>
        </property>
   </bean>
```
### Step 2 : Extractor

below graph of the batch 
```
|--------|    |----------|    |--------------|
| CSV    | => | Addition | => | Graph Writer |
|--------|    |----------|    |--------------|
```

As you can see, the job to extract data from csv is "input": 

So 2 bean is defined : input and a file InputFile
```
   <!-- Input file -->
   <bean id="fileInput" class="java.io.FileInputStream">
       <constructor-arg value="example/Population_France/Population_France.csv"/>
   </bean>
   <!-- CSV Reader -->
   <bean id="input" class="info.kapable.tools.DataReader.CSVDataReader">
       <constructor-arg ref="fileInput" />
       <constructor-arg ref="dataInputMapping" />
       <constructor-arg value=";"/>
        <property name="headerToIgnore" value="3" />
   </bean>
 ```
 
### Step 3: Transform
 
Now we need to tranform data to build date from year column and month column. So wee will sum year and month to obtain result into date. We "connect" the new additionYearAndMonthOperation to input with the properties input.

```
   <!-- Addition year column with month column to build date -->
   <bean id="additionYearAndMonthOperation" class="info.kapable.tools.DataTransform.Addition">
        <property name="input">
                <list>
                        <ref bean="input"/>
                </list>
        </property>
	<property name="dimensionToSum">
                <list>
                        <ref bean="columnYear"/>
                        <ref bean="columnMonth"/>
                </list>
        </property>
        <property name="dimensionResult" ref="columnDate"/>
   </bean>
```

### Step 3: Build Graph

Now data is OK, wee can graph value in function of date. For this job we need a last bean 

```
   <!-- Chart Writer -->
   <bean id="output" class="info.kapable.tools.DataWriter.ChartDataWriter">
        <constructor-arg><value>Population_France.jpg</value></constructor-arg>
        <property name="input">
		<list>
			<ref bean="additionYearAndMonthOperation" />
		</list>
	</property>
	<property name="xTitle"><value>Periode</value></property>
	<property name="xDimension" ref="columnDate"></property>
        <property name="chartTitle"><value>Population Francaise</value></property>
        <property name="yTitle"><value>Millier d'individus</value></property>
        <property name="dimensionToGraph">
		<util:map map-class="java.util.HashMap" key-type="java.lang.String" value-type="info.kapable.tools.pojo.Dimension">
                        <entry key="nombre d'individus" value-ref="columnValue"/>
		</util:map>
	</property>
   </bean>
```

