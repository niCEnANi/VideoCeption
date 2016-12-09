import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Naga on 19-09-2016.
  */
object ImageClassification {
  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir", "D:\\winutils")
    val sparkConf = new SparkConf().setAppName("ImageClassification").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val train = sc.textFile("data/train")
    val test = sc.textFile("data/test")

    val parsedData = train.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)  ))
    }
    val testData1 = test.map(line => {
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    })


    val trainingData = parsedData


    val numClasses = 5
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    val maxDepth = 30
    val maxBins = 32

    val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins)


    print(model.toDebugString);
    scala.tools.nsc.io.File("tree.txt").writeAll(model.toDebugString)

    val classify1 = testData1.map { line =>
      val prediction = model.predict(line.features)
      (line.label, prediction)
    }

    val p =  classify1.countByValue();
  }
}
