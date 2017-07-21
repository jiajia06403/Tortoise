// (C) Uri Wilensky. https://github.com/NetLogo/Tortoise

package org.nlogo.tortoise.dock

import
  org.json4s.{ native, string2JsonInput },
    native.JsonMethods.{ pretty, parse, render => jsRender }

import
  org.nlogo.{ core, tortoise },
    core.{ AgentKind, Model, ShapeList },
      ShapeList.shapesToMap,
    tortoise.json.JsonSerializer

import
  org.nlogo.headless.lang.FixtureSuite

import
  org.scalatest.Matchers

class JsonSerializerTests extends FixtureSuite with Matchers {

  test("JsonSerializer shapes") { implicit fixture =>

    val shapeList = new ShapeList(AgentKind.Turtle, shapesToMap(Model.defaultShapes))

    val shapes = Seq(
        "default" ->
          """|{
             |  "name":"default",
             |  "editableColorIndex":0,
             |  "rotate":true,
             |  "elements":[{
             |      "xcors":[150,40,150,260],
             |      "ycors":[5,250,205,250],
             |      "type":"polygon",
             |      "color":"rgba(141, 141, 141, 1.0)",
             |      "filled":true,
             |      "marked":true
             |  }]
             |}""".stripMargin,
        "person"  ->
          """|{
             |  "name":"person",
             |  "editableColorIndex":0,
             |  "rotate":false,
             |  "elements":[{
             |      "x":110,
             |      "y":5,
             |      "diam":80,
             |      "type":"circle",
             |      "color":"rgba(141, 141, 141, 1.0)",
             |      "filled":true,
             |      "marked":true
             |    },{
             |      "xcors":[105,120,90,105,135,150,165,195,210,180,195],
             |      "ycors":[90,195,285,300,300,225,300,300,285,195,90],
             |      "type":"polygon",
             |      "color":"rgba(141, 141, 141, 1.0)",
             |      "filled":true,
             |      "marked":true
             |    },{
             |      "xmin":127,
             |      "ymin":79,
             |      "xmax":172,
             |      "ymax":94,
             |      "type":"rectangle",
             |      "color":"rgba(141, 141, 141, 1.0)",
             |      "filled":true,
             |      "marked":true
             |    },{
             |      "xcors":[195,240,225,165],
             |      "ycors":[90,150,180,105],
             |      "type":"polygon",
             |      "color":"rgba(141, 141, 141, 1.0)",
             |      "filled":true,
             |      "marked":true
             |    },{
             |      "xcors":[105,60,75,135],
             |      "ycors":[90,150,180,105],
             |      "type":"polygon",
             |      "color":"rgba(141, 141, 141, 1.0)",
             |      "filled":true,
             |      "marked":true
             |  }]
             |}""".stripMargin
      ) map {
        case (shapeName, json) => (shapeList.shape(shapeName), jsRender(parse(json)))
      }

    shapes foreach {
      case (shape, expectedJSON) =>
        val json = jsRender(parse(JsonSerializer.serialize(shape)))
        pretty(json) should equal(pretty(expectedJSON))
    }

  }

}
