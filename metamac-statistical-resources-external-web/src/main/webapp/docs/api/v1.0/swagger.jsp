{
  "swagger": "2.0",
  "info" : {
    "description" : "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis\n\t\tdis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec\n\t\tpede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede\n\t\tmollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat\n\t\tvitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum.\n\t\tAenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum\n\t\trhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio\n\t\tet ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed\n\t\tfringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,",
    "version" : "1.2.2-SNAPSHOT",
    "title" : "API de recursos estadísticos"
  },
  "host" : "<%=org.siemac.metamac.statistical.resources.web.external.WebUtils.getApiBaseURL()%>",
  "schemes" : [],
  "tags" : [
    {
      "name" : "\/v1.0/collections",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/collections/{agencyID}",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/collections/{agencyID}/{resourceID}",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/datasets",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/datasets/{agencyID}",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/datasets/{agencyID}/{resourceID}",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/datasets/{agencyID}/{resourceID}/{version}",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/queries",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/queries/{agencyID}",
      "description" : ""
    }
    ,
    {
      "name" : "\/v1.0/queries/{agencyID}/{resourceID}",
      "description" : ""
    }
  ],
  "definitions" : {
    "json_Attribute" : {
      "type" : "object",
      "title" : "Attribute",
          "properties" : {
            "attachmentLevel" : {
"description" : "",
"$ref" : "#/definitions/json_AttributeAttachmentLevelType"
            },
            "attributeValues" : {
"description" : "",
"$ref" : "#/definitions/json_AttributeValues"
            },
            "dimensions" : {
"description" : "",
"$ref" : "#/definitions/json_AttributeDimensions"
            },
            "id" : {
"description" : "",
"type" : "string"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "type" : {
"description" : "",
"$ref" : "#/definitions/json_ComponentType"
            }
          },
      "description" : "<p>Java class for Attribute complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Attribute\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"attachmentLevel\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeAttachmentLevelType\"/>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeDimensions\" minOccurs=\"0\"/>\r\n         &lt;element name=\"attributeValues\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeValues\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}componentType\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_AttributeAttachmentLevelType" : {
      "type" : "string",
      "title" : "AttributeAttachmentLevelType",
          "enum" : [
            "DATASET",
            "DIMENSION",
            "PRIMARY_MEASURE"
          ],
      "description" : "<p>Java class for AttributeAttachmentLevelType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"AttributeAttachmentLevelType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"DATASET\"/>\r\n     &lt;enumeration value=\"DIMENSION\"/>\r\n     &lt;enumeration value=\"PRIMARY_MEASURE\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_AttributeDimension" : {
      "type" : "object",
      "title" : "AttributeDimension",
          "properties" : {
            "dimensionId" : {
"description" : "",
"type" : "string"
            },
            "values" : {
"description" : "",
"$ref" : "#/definitions/json_AttributeValues"
            }
          },
      "description" : "<p>Java class for AttributeDimension complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"AttributeDimension\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensionId\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"values\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeValues\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_AttributeDimensions" : {
      "type" : "object",
      "title" : "AttributeDimensions",
          "properties" : {
            "dimension" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_AttributeDimension"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for AttributeDimensions complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"AttributeDimensions\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimension\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeDimension\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_AttributeValues" : {
      "type" : "object",
      "title" : "AttributeValues",
      "description" : "<p>Java class for AttributeValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"AttributeValues\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Attributes" : {
      "type" : "object",
      "title" : "Attributes",
          "properties" : {
            "attribute" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_Attribute"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for Attributes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Attributes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"attribute\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Attribute\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Chapter" : {
      "type" : "object",
      "title" : "Chapter",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_CollectionNode"
        },
        {
          "properties" : {
            "nodes" : {
"description" : "",
"$ref" : "#/definitions/json_CollectionNodes"
            }
          }
        }
      ],
      "description" : "<p>Java class for Chapter complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Chapter\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNode\">\r\n       &lt;sequence>\r\n         &lt;element name=\"nodes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNodes\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_ChildLinks" : {
      "type" : "object",
      "title" : "ChildLinks",
          "properties" : {
            "childLink" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_ResourceLink"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : ""
    }
    ,
    "json_CodeRepresentation" : {
      "type" : "object",
      "title" : "CodeRepresentation",
          "properties" : {
            "code" : {
"description" : "",
"type" : "string"
            },
            "index" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for CodeRepresentation complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CodeRepresentation\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;attribute name=\"code\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n       &lt;attribute name=\"index\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}long\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_CodeRepresentations" : {
      "type" : "object",
      "title" : "CodeRepresentations",
          "properties" : {
            "representation" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_CodeRepresentation"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for CodeRepresentations complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CodeRepresentations\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"representation\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CodeRepresentation\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Collection" : {
      "type" : "object",
      "title" : "Collection",
          "properties" : {
            "childLinks" : {
"description" : "",
"$ref" : "#/definitions/json_ChildLinks"
            },
            "data" : {
"description" : "",
"$ref" : "#/definitions/json_CollectionData"
            },
            "description" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "id" : {
"description" : "",
"type" : "string"
            },
            "kind" : {
"description" : "",
"type" : "string"
            },
            "metadata" : {
"description" : "",
"$ref" : "#/definitions/json_CollectionMetadata"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "parentLink" : {
"description" : "",
"$ref" : "#/definitions/json_ResourceLink"
            },
            "selectedLanguages" : {
"description" : "",
"$ref" : "#/definitions/json_SelectedLanguages"
            },
            "selfLink" : {
"description" : "",
"$ref" : "#/definitions/json_ResourceLink"
            },
            "urn" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : "<p>Java class for Collection complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Collection\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"urn\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"selfLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"parentLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"childLinks\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ChildLinks\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"selectedLanguages\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}SelectedLanguages\"/>\r\n         &lt;element name=\"metadata\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionMetadata\" minOccurs=\"0\"/>\r\n         &lt;element name=\"data\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionData\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"kind\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_CollectionData" : {
      "type" : "object",
      "title" : "CollectionData",
          "properties" : {
            "nodes" : {
"description" : "",
"$ref" : "#/definitions/json_CollectionNodes"
            }
          },
      "description" : "<p>Java class for CollectionData complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionData\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"nodes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNodes\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_CollectionMetadata" : {
      "type" : "object",
      "title" : "CollectionMetadata",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_StatisticalResource"
        },
        {
          "properties" : {
            "formatExtentResources" : {
"description" : "",
"type" : "number"
            }
          }
        }
      ],
      "description" : "<p>Java class for CollectionMetadata complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionMetadata\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}StatisticalResource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"formatExtentResources\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_CollectionNode" : {
      "type" : "object",
      "title" : "CollectionNode",
          "properties" : {
            "description" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            }
          },
      "description" : "<p>Java class for CollectionNode complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionNode\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_CollectionNodes" : {
      "type" : "object",
      "title" : "CollectionNodes",
          "properties" : {
            "node" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_CollectionNode"
}
            }
          },
      "description" : "<p>Java class for CollectionNodes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionNodes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"node\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNode\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Collections" : {
      "type" : "object",
      "title" : "Collections",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_ListBase"
        },
        {
          "properties" : {
            "collection" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_Resource"
}
            }
          }
        }
      ],
      "description" : "<p>Java class for Collections complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Collections\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ListBase\">\r\n       &lt;sequence>\r\n         &lt;element name=\"collection\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_ComponentType" : {
      "type" : "string",
      "title" : "ComponentType",
          "enum" : [
            "OTHER",
            "SPATIAL",
            "TEMPORAL",
            "MEASURE"
          ],
      "description" : "<p>Java class for componentType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"componentType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"OTHER\"/>\r\n     &lt;enumeration value=\"SPATIAL\"/>\r\n     &lt;enumeration value=\"TEMPORAL\"/>\r\n     &lt;enumeration value=\"MEASURE\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_Data" : {
      "type" : "object",
      "title" : "Data",
          "properties" : {
            "attributes" : {
"description" : "",
"$ref" : "#/definitions/json_DataAttributes"
            },
            "dimensions" : {
"description" : "",
"$ref" : "#/definitions/json_DimensionRepresentations"
            },
            "observations" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : "<p>Java class for Data complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Data\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionRepresentations\"/>\r\n         &lt;element name=\"attributes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataAttributes\" minOccurs=\"0\"/>\r\n         &lt;element name=\"observations\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DataAttribute" : {
      "type" : "object",
      "title" : "DataAttribute",
          "properties" : {
            "id" : {
"description" : "",
"type" : "string"
            },
            "value" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : "<p>Java class for DataAttribute complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DataAttribute\">\r\n   &lt;simpleContent>\r\n     &lt;extension base=\"&lt;http://www.w3.org/2001/XMLSchema>string\">\r\n       &lt;attribute name=\"id\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/extension>\r\n   &lt;/simpleContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DataAttributes" : {
      "type" : "object",
      "title" : "DataAttributes",
          "properties" : {
            "attribute" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_DataAttribute"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for DataAttributes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DataAttributes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"attribute\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataAttribute\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DataStructureDefinition" : {
      "type" : "object",
      "title" : "DataStructureDefinition",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_Resource"
        },
        {
          "properties" : {
            "autoOpen" : {
"description" : "",
"type" : "boolean"
            },
            "heading" : {
"description" : "",
"$ref" : "#/definitions/json_DimensionsId"
            },
            "showDecimals" : {
"description" : "",
"type" : "number"
            },
            "stub" : {
"description" : "",
"$ref" : "#/definitions/json_DimensionsId"
            }
          }
        }
      ],
      "description" : "<p>Java class for DataStructureDefinition complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DataStructureDefinition\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"heading\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionsId\"/>\r\n         &lt;element name=\"stub\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionsId\"/>\r\n         &lt;element name=\"autoOpen\" type=\"{http://www.w3.org/2001/XMLSchema}boolean\" minOccurs=\"0\"/>\r\n         &lt;element name=\"showDecimals\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Dataset" : {
      "type" : "object",
      "title" : "Dataset",
          "properties" : {
            "childLinks" : {
"description" : "",
"$ref" : "#/definitions/json_ChildLinks"
            },
            "data" : {
"description" : "",
"$ref" : "#/definitions/json_Data"
            },
            "description" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "id" : {
"description" : "",
"type" : "string"
            },
            "kind" : {
"description" : "",
"type" : "string"
            },
            "metadata" : {
"description" : "",
"$ref" : "#/definitions/json_DatasetMetadata"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "parentLink" : {
"description" : "",
"$ref" : "#/definitions/json_ResourceLink"
            },
            "selectedLanguages" : {
"description" : "",
"$ref" : "#/definitions/json_SelectedLanguages"
            },
            "selfLink" : {
"description" : "",
"$ref" : "#/definitions/json_ResourceLink"
            },
            "urn" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : "<p>Java class for Dataset complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Dataset\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"urn\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"selfLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"parentLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"childLinks\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ChildLinks\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"selectedLanguages\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}SelectedLanguages\"/>\r\n         &lt;element name=\"metadata\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DatasetMetadata\" minOccurs=\"0\"/>\r\n         &lt;element name=\"data\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Data\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"kind\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DatasetMetadata" : {
      "type" : "object",
      "title" : "DatasetMetadata",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_StatisticalResource"
        },
        {
          "properties" : {
            "attributes" : {
"description" : "",
"$ref" : "#/definitions/json_Attributes"
            },
            "bibliographicCitation" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "dateEnd" : {
"description" : "",
"type" : "string"
            },
            "dateNextUpdate" : {
"description" : "",
"type" : "string"
            },
            "dateStart" : {
"description" : "",
"type" : "string"
            },
            "dimensions" : {
"description" : "",
"$ref" : "#/definitions/json_Dimensions"
            },
            "formatExtentDimensions" : {
"description" : "",
"type" : "number"
            },
            "formatExtentObservations" : {
"description" : "",
"type" : "number"
            },
            "geographicCoverages" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "geographicGranularities" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "isReplacedByVersion" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "isRequiredBy" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "measureCoverages" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "relatedDsd" : {
"description" : "",
"$ref" : "#/definitions/json_DataStructureDefinition"
            },
            "replacesVersion" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "statisticOfficiality" : {
"description" : "",
"$ref" : "#/definitions/json_Item"
            },
            "statisticalUnit" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "subjectAreas" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "temporalCoverages" : {
"description" : "",
"$ref" : "#/definitions/json_Items"
            },
            "temporalGranularities" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "updateFrequency" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for DatasetMetadata complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DatasetMetadata\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}StatisticalResource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"replacesVersion\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" minOccurs=\"0\"/>\r\n         &lt;element name=\"isReplacedByVersion\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" minOccurs=\"0\"/>\r\n         &lt;element name=\"isRequiredBy\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"geographicCoverages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"temporalCoverages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Items\"/>\r\n         &lt;element name=\"measureCoverages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"geographicGranularities\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"temporalGranularities\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"dateStart\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"dateEnd\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"statisticalUnit\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"subjectAreas\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"relatedDsd\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataStructureDefinition\"/>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Dimensions\"/>\r\n         &lt;element name=\"attributes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Attributes\" minOccurs=\"0\"/>\r\n         &lt;element name=\"formatExtentObservations\" type=\"{http://www.w3.org/2001/XMLSchema}long\" minOccurs=\"0\"/>\r\n         &lt;element name=\"formatExtentDimensions\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n         &lt;element name=\"dateNextUpdate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"updateFrequency\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"statisticOfficiality\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Item\"/>\r\n         &lt;element name=\"bibliographicCitation\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Datasets" : {
      "type" : "object",
      "title" : "Datasets",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_ListBase"
        },
        {
          "properties" : {
            "dataset" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_Resource"
}
            }
          }
        }
      ],
      "description" : "<p>Java class for Datasets complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Datasets\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ListBase\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dataset\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Dimension" : {
      "type" : "object",
      "title" : "Dimension",
          "properties" : {
            "dimensionValues" : {
"description" : "",
"$ref" : "#/definitions/json_DimensionValues"
            },
            "id" : {
"description" : "",
"type" : "string"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "type" : {
"description" : "",
"$ref" : "#/definitions/json_DimensionType"
            },
            "variable" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            }
          },
      "description" : "<p>Java class for Dimension complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Dimension\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionType\"/>\r\n         &lt;element name=\"variable\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" minOccurs=\"0\"/>\r\n         &lt;element name=\"dimensionValues\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionValues\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DimensionRepresentation" : {
      "type" : "object",
      "title" : "DimensionRepresentation",
          "properties" : {
            "dimensionId" : {
"description" : "",
"type" : "string"
            },
            "representations" : {
"description" : "",
"$ref" : "#/definitions/json_CodeRepresentations"
            }
          },
      "description" : "<p>Java class for DimensionRepresentation complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionRepresentation\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensionId\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"representations\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CodeRepresentations\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DimensionRepresentations" : {
      "type" : "object",
      "title" : "DimensionRepresentations",
          "properties" : {
            "dimension" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_DimensionRepresentation"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for DimensionRepresentations complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionRepresentations\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimension\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionRepresentation\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DimensionType" : {
      "type" : "string",
      "title" : "DimensionType",
          "enum" : [
            "MEASURE_DIMENSION",
            "TIME_DIMENSION",
            "GEOGRAPHIC_DIMENSION",
            "DIMENSION"
          ],
      "description" : "<p>Java class for DimensionType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"DimensionType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"MEASURE_DIMENSION\"/>\r\n     &lt;enumeration value=\"TIME_DIMENSION\"/>\r\n     &lt;enumeration value=\"GEOGRAPHIC_DIMENSION\"/>\r\n     &lt;enumeration value=\"DIMENSION\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_DimensionValues" : {
      "type" : "object",
      "title" : "DimensionValues",
      "description" : "<p>Java class for DimensionValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionValues\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Dimensions" : {
      "type" : "object",
      "title" : "Dimensions",
          "properties" : {
            "dimension" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_Dimension"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for Dimensions complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Dimensions\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimension\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Dimension\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_DimensionsId" : {
      "type" : "object",
      "title" : "DimensionsId",
          "properties" : {
            "dimensionId" : {
"description" : "",
"type" : "array",
"items" : {
  "type" : "string"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for DimensionsId complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionsId\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensionId\" type=\"{http://www.w3.org/2001/XMLSchema}string\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_EnumeratedAttributeValue" : {
      "type" : "object",
      "title" : "EnumeratedAttributeValue",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_Resource"
        },
        {
          "properties" : {
            "measureQuantity" : {
"description" : "",
"$ref" : "#/definitions/json_MeasureQuantity"
            }
          }
        }
      ],
      "description" : "<p>Java class for EnumeratedAttributeValue complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"EnumeratedAttributeValue\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"measureQuantity\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}MeasureQuantity\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_EnumeratedAttributeValues" : {
      "type" : "object",
      "title" : "EnumeratedAttributeValues",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_AttributeValues"
        },
        {
          "properties" : {
            "total" : {
"description" : "",
"type" : "number"
            },
            "value" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_EnumeratedAttributeValue"
}
            }
          }
        }
      ],
      "description" : "<p>Java class for EnumeratedAttributeValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"EnumeratedAttributeValues\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeValues\">\r\n       &lt;sequence>\r\n         &lt;element name=\"value\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}EnumeratedAttributeValue\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_EnumeratedDimensionValue" : {
      "type" : "object",
      "title" : "EnumeratedDimensionValue",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_Resource"
        },
        {
          "properties" : {
            "measureQuantity" : {
"description" : "",
"$ref" : "#/definitions/json_MeasureQuantity"
            },
            "open" : {
"description" : "",
"type" : "boolean"
            },
            "showDecimalsPrecision" : {
"description" : "",
"type" : "number"
            },
            "variableElement" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "visualisationParent" : {
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for EnumeratedDimensionValue complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"EnumeratedDimensionValue\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"visualisationParent\" type=\"{http://www.w3.org/2001/XMLSchema}string\" minOccurs=\"0\"/>\r\n         &lt;element name=\"variableElement\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" minOccurs=\"0\"/>\r\n         &lt;element name=\"showDecimalsPrecision\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n         &lt;element name=\"open\" type=\"{http://www.w3.org/2001/XMLSchema}boolean\" minOccurs=\"0\"/>\r\n         &lt;element name=\"measureQuantity\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}MeasureQuantity\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_EnumeratedDimensionValues" : {
      "type" : "object",
      "title" : "EnumeratedDimensionValues",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_DimensionValues"
        },
        {
          "properties" : {
            "total" : {
"description" : "",
"type" : "number"
            },
            "value" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_EnumeratedDimensionValue"
}
            }
          }
        }
      ],
      "description" : "<p>Java class for EnumeratedDimensionValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"EnumeratedDimensionValues\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionValues\">\r\n       &lt;sequence>\r\n         &lt;element name=\"value\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}EnumeratedDimensionValue\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_InternationalString" : {
      "type" : "object",
      "title" : "InternationalString",
          "properties" : {
            "text" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_LocalisedString"
}
            }
          },
      "description" : ""
    }
    ,
    "json_Item" : {
      "type" : "object",
      "title" : "Item",
          "properties" : {
            "id" : {
"description" : "",
"type" : "string"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            }
          },
      "description" : ""
    }
    ,
    "json_ItemResource" : {
      "type" : "object",
      "title" : "ItemResource",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_Resource"
        },
        {
          "properties" : {
            "parent" : {
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for ItemResource complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"ItemResource\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"parent\" type=\"{http://www.w3.org/2001/XMLSchema}string\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Items" : {
      "type" : "object",
      "title" : "Items",
          "properties" : {
            "item" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_Item"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : ""
    }
    ,
    "json_ListBase" : {
      "type" : "object",
      "title" : "ListBase",
          "properties" : {
            "firstLink" : {
"description" : "",
"type" : "string"
            },
            "kind" : {
"description" : "",
"type" : "string"
            },
            "lastLink" : {
"description" : "",
"type" : "string"
            },
            "limit" : {
"description" : "",
"type" : "number"
            },
            "nextLink" : {
"description" : "",
"type" : "string"
            },
            "offset" : {
"description" : "",
"type" : "number"
            },
            "previousLink" : {
"description" : "",
"type" : "string"
            },
            "selfLink" : {
"description" : "",
"type" : "string"
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : ""
    }
    ,
    "json_LocalisedString" : {
      "type" : "object",
      "title" : "LocalisedString",
          "properties" : {
            "lang" : {
"description" : "",
"type" : "string"
            },
            "value" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : ""
    }
    ,
    "json_MeasureQuantity" : {
      "type" : "object",
      "title" : "MeasureQuantity",
          "properties" : {
            "unitCode" : {
"description" : "",
"$ref" : "#/definitions/json_ItemResource"
            }
          },
      "description" : "<p>Java class for MeasureQuantity complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"MeasureQuantity\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"unitCode\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}ItemResource\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_NextVersionType" : {
      "type" : "string",
      "title" : "NextVersionType",
          "enum" : [
            "NO_UPDATES",
            "NON_SCHEDULED_UPDATE",
            "SCHEDULED_UPDATE"
          ],
      "description" : "<p>Java class for NextVersionType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"NextVersionType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"NO_UPDATES\"/>\r\n     &lt;enumeration value=\"NON_SCHEDULED_UPDATE\"/>\r\n     &lt;enumeration value=\"SCHEDULED_UPDATE\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_NonEnumeratedAttributeValue" : {
      "type" : "object",
      "title" : "NonEnumeratedAttributeValue",
          "properties" : {
            "id" : {
"description" : "",
"type" : "string"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            }
          },
      "description" : "<p>Java class for NonEnumeratedAttributeValue complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"NonEnumeratedAttributeValue\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_NonEnumeratedAttributeValues" : {
      "type" : "object",
      "title" : "NonEnumeratedAttributeValues",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_AttributeValues"
        },
        {
          "properties" : {
            "total" : {
"description" : "",
"type" : "number"
            },
            "value" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_NonEnumeratedAttributeValue"
}
            }
          }
        }
      ],
      "description" : "<p>Java class for NonEnumeratedAttributeValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"NonEnumeratedAttributeValues\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeValues\">\r\n       &lt;sequence>\r\n         &lt;element name=\"value\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}NonEnumeratedAttributeValue\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_NonEnumeratedDimensionValue" : {
      "type" : "object",
      "title" : "NonEnumeratedDimensionValue",
          "properties" : {
            "id" : {
"description" : "",
"type" : "string"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            }
          },
      "description" : "<p>Java class for NonEnumeratedDimensionValue complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"NonEnumeratedDimensionValue\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_NonEnumeratedDimensionValues" : {
      "type" : "object",
      "title" : "NonEnumeratedDimensionValues",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_DimensionValues"
        },
        {
          "properties" : {
            "total" : {
"description" : "",
"type" : "number"
            },
            "value" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_NonEnumeratedDimensionValue"
}
            }
          }
        }
      ],
      "description" : "<p>Java class for NonEnumeratedDimensionValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"NonEnumeratedDimensionValues\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionValues\">\r\n       &lt;sequence>\r\n         &lt;element name=\"value\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}NonEnumeratedDimensionValue\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Queries" : {
      "type" : "object",
      "title" : "Queries",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_ListBase"
        },
        {
          "properties" : {
            "query" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_Resource"
}
            }
          }
        }
      ],
      "description" : "<p>Java class for Queries complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Queries\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ListBase\">\r\n       &lt;sequence>\r\n         &lt;element name=\"query\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_Query" : {
      "type" : "object",
      "title" : "Query",
          "properties" : {
            "childLinks" : {
"description" : "",
"$ref" : "#/definitions/json_ChildLinks"
            },
            "data" : {
"description" : "",
"$ref" : "#/definitions/json_Data"
            },
            "description" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "id" : {
"description" : "",
"type" : "string"
            },
            "kind" : {
"description" : "",
"type" : "string"
            },
            "metadata" : {
"description" : "",
"$ref" : "#/definitions/json_QueryMetadata"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "parentLink" : {
"description" : "",
"$ref" : "#/definitions/json_ResourceLink"
            },
            "selectedLanguages" : {
"description" : "",
"$ref" : "#/definitions/json_SelectedLanguages"
            },
            "selfLink" : {
"description" : "",
"$ref" : "#/definitions/json_ResourceLink"
            },
            "urn" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : "<p>Java class for Query complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Query\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"urn\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"selfLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"parentLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"childLinks\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ChildLinks\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"selectedLanguages\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}SelectedLanguages\"/>\r\n         &lt;element name=\"metadata\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}QueryMetadata\" minOccurs=\"0\"/>\r\n         &lt;element name=\"data\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Data\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"kind\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_QueryMetadata" : {
      "type" : "object",
      "title" : "QueryMetadata",
          "properties" : {
            "attributes" : {
"description" : "",
"$ref" : "#/definitions/json_Attributes"
            },
            "dimensions" : {
"description" : "",
"$ref" : "#/definitions/json_Dimensions"
            },
            "isPartOf" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "latestDataNumber" : {
"description" : "",
"type" : "number"
            },
            "maintainer" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "relatedDataset" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "relatedDsd" : {
"description" : "",
"$ref" : "#/definitions/json_DataStructureDefinition"
            },
            "requires" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "statisticalOperation" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "status" : {
"description" : "",
"$ref" : "#/definitions/json_QueryStatus"
            },
            "type" : {
"description" : "",
"$ref" : "#/definitions/json_QueryType"
            },
            "validFrom" : {
"description" : "",
"type" : "string"
            },
            "validTo" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : "<p>Java class for QueryMetadata complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"QueryMetadata\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"relatedDataset\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"relatedDsd\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataStructureDefinition\"/>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Dimensions\"/>\r\n         &lt;element name=\"attributes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Attributes\" minOccurs=\"0\"/>\r\n         &lt;element name=\"status\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}QueryStatus\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}QueryType\"/>\r\n         &lt;element name=\"latestDataNumber\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n         &lt;element name=\"requires\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"isPartOf\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"statisticalOperation\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"maintainer\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"validFrom\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"validTo\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_QueryStatus" : {
      "type" : "string",
      "title" : "QueryStatus",
          "enum" : [
            "ACTIVE",
            "DISCONTINUED"
          ],
      "description" : "<p>Java class for QueryStatus.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"QueryStatus\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"ACTIVE\"/>\r\n     &lt;enumeration value=\"DISCONTINUED\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_QueryType" : {
      "type" : "string",
      "title" : "QueryType",
          "enum" : [
            "AUTOINCREMENTAL",
            "LATEST_DATA",
            "FIXED"
          ],
      "description" : "<p>Java class for QueryType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"QueryType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"AUTOINCREMENTAL\"/>\r\n     &lt;enumeration value=\"LATEST_DATA\"/>\r\n     &lt;enumeration value=\"FIXED\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_Resource" : {
      "type" : "object",
      "title" : "Resource",
          "properties" : {
            "id" : {
"description" : "",
"type" : "string"
            },
            "kind" : {
"description" : "",
"type" : "string"
            },
            "name" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "nestedId" : {
"description" : "",
"type" : "string"
            },
            "selfLink" : {
"description" : "",
"$ref" : "#/definitions/json_ResourceLink"
            },
            "urn" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : ""
    }
    ,
    "json_ResourceLink" : {
      "type" : "object",
      "title" : "ResourceLink",
          "properties" : {
            "href" : {
"description" : "",
"type" : "string"
            },
            "kind" : {
"description" : "",
"type" : "string"
            }
          },
      "description" : ""
    }
    ,
    "json_Resources" : {
      "type" : "object",
      "title" : "Resources",
          "properties" : {
            "resource" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_Resource"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : ""
    }
    ,
    "json_SelectedLanguages" : {
      "type" : "object",
      "title" : "SelectedLanguages",
          "properties" : {
            "language" : {
"description" : "",
"type" : "array",
"items" : {
  "type" : "string"
}
            },
            "total" : {
"description" : "",
"type" : "number"
            }
          },
      "description" : "<p>Java class for SelectedLanguages complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"SelectedLanguages\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"language\" type=\"{http://www.w3.org/2001/XMLSchema}string\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_StatisticalResource" : {
      "type" : "object",
      "title" : "StatisticalResource",
          "properties" : {
            "abstract" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "accessRights" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "conformsTo" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "contributors" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "copyrightDate" : {
"description" : "",
"type" : "number"
            },
            "createdDate" : {
"description" : "",
"type" : "string"
            },
            "creator" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "hasPart" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "isPartOf" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "isReplacedBy" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "keywords" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "language" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "languages" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "lastUpdate" : {
"description" : "",
"type" : "string"
            },
            "license" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "maintainer" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "mediators" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "newnessUntilDate" : {
"description" : "",
"type" : "string"
            },
            "nextVersion" : {
"description" : "",
"$ref" : "#/definitions/json_NextVersionType"
            },
            "publisherContributors" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "publishers" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "replaces" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "rightsHolder" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "statisticalOperation" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "statisticalOperationInstances" : {
"description" : "",
"$ref" : "#/definitions/json_Resources"
            },
            "subtitle" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "titleAlternative" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "type" : {
"description" : "",
"$ref" : "#/definitions/json_StatisticalResourceType"
            },
            "validFrom" : {
"description" : "",
"type" : "string"
            },
            "validTo" : {
"description" : "",
"type" : "string"
            },
            "version" : {
"description" : "",
"type" : "string"
            },
            "versionRationale" : {
"description" : "",
"$ref" : "#/definitions/json_InternationalString"
            },
            "versionRationaleTypes" : {
"description" : "",
"$ref" : "#/definitions/json_VersionRationaleTypes"
            }
          },
      "description" : "<p>Java class for StatisticalResource complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"StatisticalResource\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"language\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"languages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"statisticalOperation\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"statisticalOperationInstances\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"subtitle\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"titleAlternative\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"abstract\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"keywords\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}StatisticalResourceType\"/>\r\n         &lt;element name=\"creator\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"contributors\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"createdDate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"lastUpdate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"conformsTo\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"publishers\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"publisherContributors\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"mediators\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"newnessUntilDate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\" minOccurs=\"0\"/>\r\n         &lt;element name=\"replaces\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"isReplacedBy\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"hasPart\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"isPartOf\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"rightsHolder\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"copyrightDate\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n         &lt;element name=\"license\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"accessRights\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"maintainer\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"version\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"versionRationaleTypes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}VersionRationaleTypes\"/>\r\n         &lt;element name=\"versionRationale\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"validFrom\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"validTo\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\" minOccurs=\"0\"/>\r\n         &lt;element name=\"nextVersion\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}NextVersionType\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_StatisticalResourceType" : {
      "type" : "string",
      "title" : "StatisticalResourceType",
          "enum" : [
            "DATASET",
            "COLLECTION",
            "QUERY"
          ],
      "description" : "<p>Java class for StatisticalResourceType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"StatisticalResourceType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"DATASET\"/>\r\n     &lt;enumeration value=\"COLLECTION\"/>\r\n     &lt;enumeration value=\"QUERY\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_Table" : {
      "type" : "object",
      "title" : "Table",
      "allOf" : [
        {
          "$ref" : "#/definitions/json_CollectionNode"
        },
        {
          "properties" : {
            "dataset" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            },
            "query" : {
"description" : "",
"$ref" : "#/definitions/json_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for Table complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Table\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNode\">\r\n       &lt;choice>\r\n         &lt;sequence>\r\n           &lt;element name=\"dataset\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n           &lt;element name=\"query\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;/sequence>\r\n       &lt;/choice>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "json_VersionRationaleType" : {
      "type" : "string",
      "title" : "VersionRationaleType",
          "enum" : [
            "MAJOR_NEW_RESOURCE",
            "MAJOR_ESTIMATORS",
            "MAJOR_CATEGORIES",
            "MAJOR_VARIABLES",
            "MAJOR_OTHER",
            "MINOR_ERRATA",
            "MINOR_METADATA",
            "MINOR_DATA_UPDATE",
            "MINOR_SERIES_UPDATE",
            "MINOR_OTHER"
          ],
      "description" : "<p>Java class for VersionRationaleType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"VersionRationaleType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"MAJOR_NEW_RESOURCE\"/>\r\n     &lt;enumeration value=\"MAJOR_ESTIMATORS\"/>\r\n     &lt;enumeration value=\"MAJOR_CATEGORIES\"/>\r\n     &lt;enumeration value=\"MAJOR_VARIABLES\"/>\r\n     &lt;enumeration value=\"MAJOR_OTHER\"/>\r\n     &lt;enumeration value=\"MINOR_ERRATA\"/>\r\n     &lt;enumeration value=\"MINOR_METADATA\"/>\r\n     &lt;enumeration value=\"MINOR_DATA_UPDATE\"/>\r\n     &lt;enumeration value=\"MINOR_SERIES_UPDATE\"/>\r\n     &lt;enumeration value=\"MINOR_OTHER\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "json_VersionRationaleTypes" : {
      "type" : "object",
      "title" : "VersionRationaleTypes",
          "properties" : {
            "total" : {
"description" : "",
"type" : "number"
            },
            "versionRationaleType" : {
"description" : "",
"type" : "array",
"items" : {
  "$ref" : "#/definitions/json_VersionRationaleType"
}
            }
          },
      "description" : "<p>Java class for VersionRationaleTypes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"VersionRationaleTypes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"versionRationaleType\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}VersionRationaleType\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_cdomain_ChildLinks" : {
      "type" : "object",
      "title" : "ChildLinks",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "childLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_InternationalString" : {
      "type" : "object",
      "title" : "InternationalString",
      "allOf" : [
        {
          "properties" : {
            "text" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_LocalisedString"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_Item" : {
      "type" : "object",
      "title" : "Item",
      "allOf" : [
        {
          "properties" : {
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_Items" : {
      "type" : "object",
      "title" : "Items",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "item" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Item"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_ListBase" : {
      "type" : "object",
      "title" : "ListBase",
      "allOf" : [
        {
          "properties" : {
            "firstLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "lastLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "limit" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "nextLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "offset" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "previousLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "selfLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_LocalisedString" : {
      "type" : "object",
      "title" : "LocalisedString",
      "allOf" : [
        {
          "properties" : {
            "lang" : {
              "xml" : {
                "attribute" : true,
                "namespace" : "http://www.w3.org/XML/1998/namespace"
              },
"description" : "",
"type" : "string"
            },
            "(value)" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_Resource" : {
      "type" : "object",
      "title" : "Resource",
      "allOf" : [
        {
          "properties" : {
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "nestedId" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "selfLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "urn" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_ResourceLink" : {
      "type" : "object",
      "title" : "ResourceLink",
      "allOf" : [
        {
          "properties" : {
            "href" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_Resources" : {
      "type" : "object",
      "title" : "Resources",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "resource" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_stat_Attribute" : {
      "type" : "object",
      "title" : "Attribute",
      "allOf" : [
        {
          "properties" : {
            "attachmentLevel" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_AttributeAttachmentLevelType"
            },
            "attributeValues" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_AttributeValues"
            },
            "dimensions" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_AttributeDimensions"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "type" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_componentType"
            }
          }
        }
      ],
      "description" : "<p>Java class for Attribute complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Attribute\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"attachmentLevel\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeAttachmentLevelType\"/>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeDimensions\" minOccurs=\"0\"/>\r\n         &lt;element name=\"attributeValues\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeValues\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}componentType\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_AttributeAttachmentLevelType" : {
      "type" : "string",
      "title" : "AttributeAttachmentLevelType",
          "enum" : [
            "DATASET",
            "DIMENSION",
            "PRIMARY_MEASURE"
          ],
      "description" : "<p>Java class for AttributeAttachmentLevelType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"AttributeAttachmentLevelType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"DATASET\"/>\r\n     &lt;enumeration value=\"DIMENSION\"/>\r\n     &lt;enumeration value=\"PRIMARY_MEASURE\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_AttributeDimension" : {
      "type" : "object",
      "title" : "AttributeDimension",
      "allOf" : [
        {
          "properties" : {
            "dimensionId" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "values" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_AttributeValues"
            }
          }
        }
      ],
      "description" : "<p>Java class for AttributeDimension complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"AttributeDimension\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensionId\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"values\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeValues\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_AttributeDimensions" : {
      "type" : "object",
      "title" : "AttributeDimensions",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "dimension" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_AttributeDimension"
            }
          }
        }
      ],
      "description" : "<p>Java class for AttributeDimensions complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"AttributeDimensions\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimension\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeDimension\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_AttributeValues" : {
      "type" : "object",
      "title" : "AttributeValues",
      "allOf" : [
        {
        }
      ],
      "description" : "<p>Java class for AttributeValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"AttributeValues\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Attributes" : {
      "type" : "object",
      "title" : "Attributes",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "attribute" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Attribute"
            }
          }
        }
      ],
      "description" : "<p>Java class for Attributes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Attributes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"attribute\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Attribute\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_CodeRepresentation" : {
      "type" : "object",
      "title" : "CodeRepresentation",
      "allOf" : [
        {
          "properties" : {
            "code" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "index" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            }
          }
        }
      ],
      "description" : "<p>Java class for CodeRepresentation complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CodeRepresentation\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;attribute name=\"code\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n       &lt;attribute name=\"index\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}long\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_CodeRepresentations" : {
      "type" : "object",
      "title" : "CodeRepresentations",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "representation" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_CodeRepresentation"
            }
          }
        }
      ],
      "description" : "<p>Java class for CodeRepresentations complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CodeRepresentations\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"representation\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CodeRepresentation\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Collection" : {
      "type" : "object",
      "title" : "Collection",
      "allOf" : [
        {
          "properties" : {
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "childLinks" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ChildLinks"
            },
            "data" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_CollectionData"
            },
            "description" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "metadata" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_CollectionMetadata"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "parentLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "selectedLanguages" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_SelectedLanguages"
            },
            "selfLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "urn" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for Collection complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Collection\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"urn\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"selfLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"parentLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"childLinks\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ChildLinks\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"selectedLanguages\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}SelectedLanguages\"/>\r\n         &lt;element name=\"metadata\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionMetadata\" minOccurs=\"0\"/>\r\n         &lt;element name=\"data\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionData\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"kind\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_CollectionData" : {
      "type" : "object",
      "title" : "CollectionData",
      "allOf" : [
        {
          "properties" : {
            "nodes" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_CollectionNodes"
            }
          }
        }
      ],
      "description" : "<p>Java class for CollectionData complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionData\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"nodes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNodes\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_CollectionMetadata" : {
      "type" : "object",
      "title" : "CollectionMetadata",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_stat_StatisticalResource"
        },
        {
          "properties" : {
            "formatExtentResources" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "number"
            }
          }
        }
      ],
      "description" : "<p>Java class for CollectionMetadata complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionMetadata\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}StatisticalResource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"formatExtentResources\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_CollectionNode" : {
      "type" : "object",
      "title" : "CollectionNode",
      "allOf" : [
        {
          "properties" : {
            "description" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            }
          }
        }
      ],
      "description" : "<p>Java class for CollectionNode complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionNode\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_CollectionNodes" : {
      "type" : "object",
      "title" : "CollectionNodes",
      "allOf" : [
        {
          "properties" : {
            "node" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_CollectionNode"
            }
          }
        }
      ],
      "description" : "<p>Java class for CollectionNodes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"CollectionNodes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"node\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNode\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Collections" : {
      "type" : "object",
      "title" : "Collections",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_ListBase"
        },
        {
          "properties" : {
            "collection" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for Collections complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Collections\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ListBase\">\r\n       &lt;sequence>\r\n         &lt;element name=\"collection\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_componentType" : {
      "type" : "string",
      "title" : "componentType",
          "enum" : [
            "OTHER",
            "SPATIAL",
            "TEMPORAL",
            "MEASURE"
          ],
      "description" : "<p>Java class for componentType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"componentType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"OTHER\"/>\r\n     &lt;enumeration value=\"SPATIAL\"/>\r\n     &lt;enumeration value=\"TEMPORAL\"/>\r\n     &lt;enumeration value=\"MEASURE\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Data" : {
      "type" : "object",
      "title" : "Data",
      "allOf" : [
        {
          "properties" : {
            "attributes" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DataAttributes"
            },
            "dimensions" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DimensionRepresentations"
            },
            "observations" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for Data complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Data\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionRepresentations\"/>\r\n         &lt;element name=\"attributes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataAttributes\" minOccurs=\"0\"/>\r\n         &lt;element name=\"observations\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DataAttribute" : {
      "type" : "object",
      "title" : "DataAttribute",
      "allOf" : [
        {
          "properties" : {
            "id" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "(value)" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for DataAttribute complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DataAttribute\">\r\n   &lt;simpleContent>\r\n     &lt;extension base=\"&lt;http://www.w3.org/2001/XMLSchema>string\">\r\n       &lt;attribute name=\"id\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/extension>\r\n   &lt;/simpleContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DataAttributes" : {
      "type" : "object",
      "title" : "DataAttributes",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "attribute" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DataAttribute"
            }
          }
        }
      ],
      "description" : "<p>Java class for DataAttributes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DataAttributes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"attribute\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataAttribute\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DataStructureDefinition" : {
      "type" : "object",
      "title" : "DataStructureDefinition",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_Resource"
        },
        {
          "properties" : {
            "autoOpen" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "boolean"
            },
            "heading" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DimensionsId"
            },
            "showDecimals" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "number"
            },
            "stub" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DimensionsId"
            }
          }
        }
      ],
      "description" : "<p>Java class for DataStructureDefinition complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DataStructureDefinition\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"heading\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionsId\"/>\r\n         &lt;element name=\"stub\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionsId\"/>\r\n         &lt;element name=\"autoOpen\" type=\"{http://www.w3.org/2001/XMLSchema}boolean\" minOccurs=\"0\"/>\r\n         &lt;element name=\"showDecimals\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Dataset" : {
      "type" : "object",
      "title" : "Dataset",
      "allOf" : [
        {
          "properties" : {
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "childLinks" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ChildLinks"
            },
            "data" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Data"
            },
            "description" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "metadata" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DatasetMetadata"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "parentLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "selectedLanguages" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_SelectedLanguages"
            },
            "selfLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "urn" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for Dataset complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Dataset\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"urn\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"selfLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"parentLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"childLinks\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ChildLinks\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"selectedLanguages\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}SelectedLanguages\"/>\r\n         &lt;element name=\"metadata\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DatasetMetadata\" minOccurs=\"0\"/>\r\n         &lt;element name=\"data\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Data\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"kind\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DatasetMetadata" : {
      "type" : "object",
      "title" : "DatasetMetadata",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_stat_StatisticalResource"
        },
        {
          "properties" : {
            "attributes" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Attributes"
            },
            "bibliographicCitation" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "dateEnd" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "dateNextUpdate" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "dateStart" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "dimensions" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Dimensions"
            },
            "formatExtentDimensions" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "number"
            },
            "formatExtentObservations" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "number"
            },
            "geographicCoverages" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "geographicGranularities" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "isReplacedByVersion" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "isRequiredBy" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "measureCoverages" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "relatedDsd" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DataStructureDefinition"
            },
            "replacesVersion" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "statisticOfficiality" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Item"
            },
            "statisticalUnit" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "subjectAreas" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "temporalCoverages" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Items"
            },
            "temporalGranularities" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "updateFrequency" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for DatasetMetadata complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DatasetMetadata\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}StatisticalResource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"replacesVersion\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" minOccurs=\"0\"/>\r\n         &lt;element name=\"isReplacedByVersion\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" minOccurs=\"0\"/>\r\n         &lt;element name=\"isRequiredBy\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"geographicCoverages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"temporalCoverages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Items\"/>\r\n         &lt;element name=\"measureCoverages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"geographicGranularities\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"temporalGranularities\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"dateStart\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"dateEnd\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"statisticalUnit\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"subjectAreas\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"relatedDsd\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataStructureDefinition\"/>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Dimensions\"/>\r\n         &lt;element name=\"attributes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Attributes\" minOccurs=\"0\"/>\r\n         &lt;element name=\"formatExtentObservations\" type=\"{http://www.w3.org/2001/XMLSchema}long\" minOccurs=\"0\"/>\r\n         &lt;element name=\"formatExtentDimensions\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n         &lt;element name=\"dateNextUpdate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"updateFrequency\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"statisticOfficiality\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Item\"/>\r\n         &lt;element name=\"bibliographicCitation\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Datasets" : {
      "type" : "object",
      "title" : "Datasets",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_ListBase"
        },
        {
          "properties" : {
            "dataset" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for Datasets complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Datasets\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ListBase\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dataset\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Dimension" : {
      "type" : "object",
      "title" : "Dimension",
      "allOf" : [
        {
          "properties" : {
            "dimensionValues" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DimensionValues"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "type" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DimensionType"
            },
            "variable" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for Dimension complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Dimension\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionType\"/>\r\n         &lt;element name=\"variable\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" minOccurs=\"0\"/>\r\n         &lt;element name=\"dimensionValues\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionValues\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DimensionRepresentation" : {
      "type" : "object",
      "title" : "DimensionRepresentation",
      "allOf" : [
        {
          "properties" : {
            "dimensionId" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "representations" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_CodeRepresentations"
            }
          }
        }
      ],
      "description" : "<p>Java class for DimensionRepresentation complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionRepresentation\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensionId\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"representations\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CodeRepresentations\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DimensionRepresentations" : {
      "type" : "object",
      "title" : "DimensionRepresentations",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "dimension" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DimensionRepresentation"
            }
          }
        }
      ],
      "description" : "<p>Java class for DimensionRepresentations complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionRepresentations\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimension\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionRepresentation\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DimensionType" : {
      "type" : "string",
      "title" : "DimensionType",
          "enum" : [
            "MEASURE_DIMENSION",
            "TIME_DIMENSION",
            "GEOGRAPHIC_DIMENSION",
            "DIMENSION"
          ],
      "description" : "<p>Java class for DimensionType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"DimensionType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"MEASURE_DIMENSION\"/>\r\n     &lt;enumeration value=\"TIME_DIMENSION\"/>\r\n     &lt;enumeration value=\"GEOGRAPHIC_DIMENSION\"/>\r\n     &lt;enumeration value=\"DIMENSION\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DimensionValues" : {
      "type" : "object",
      "title" : "DimensionValues",
      "allOf" : [
        {
        }
      ],
      "description" : "<p>Java class for DimensionValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionValues\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Dimensions" : {
      "type" : "object",
      "title" : "Dimensions",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "dimension" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Dimension"
            }
          }
        }
      ],
      "description" : "<p>Java class for Dimensions complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Dimensions\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimension\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Dimension\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_DimensionsId" : {
      "type" : "object",
      "title" : "DimensionsId",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "dimensionId" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for DimensionsId complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"DimensionsId\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"dimensionId\" type=\"{http://www.w3.org/2001/XMLSchema}string\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_EnumeratedAttributeValue" : {
      "type" : "object",
      "title" : "EnumeratedAttributeValue",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_Resource"
        },
        {
          "properties" : {
            "measureQuantity" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_MeasureQuantity"
            }
          }
        }
      ],
      "description" : "<p>Java class for EnumeratedAttributeValue complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"EnumeratedAttributeValue\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"measureQuantity\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}MeasureQuantity\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_EnumeratedAttributeValues" : {
      "type" : "object",
      "title" : "EnumeratedAttributeValues",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_stat_AttributeValues"
        },
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "value" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_EnumeratedAttributeValue"
            }
          }
        }
      ],
      "description" : "<p>Java class for EnumeratedAttributeValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"EnumeratedAttributeValues\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}AttributeValues\">\r\n       &lt;sequence>\r\n         &lt;element name=\"value\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}EnumeratedAttributeValue\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_ItemResource" : {
      "type" : "object",
      "title" : "ItemResource",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_Resource"
        },
        {
          "properties" : {
            "parent" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for ItemResource complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"ItemResource\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"parent\" type=\"{http://www.w3.org/2001/XMLSchema}string\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_MeasureQuantity" : {
      "type" : "object",
      "title" : "MeasureQuantity",
      "allOf" : [
        {
          "properties" : {
            "unitCode" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_ItemResource"
            }
          }
        }
      ],
      "description" : "<p>Java class for MeasureQuantity complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"MeasureQuantity\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"unitCode\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}ItemResource\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_NextVersionType" : {
      "type" : "string",
      "title" : "NextVersionType",
          "enum" : [
            "NO_UPDATES",
            "NON_SCHEDULED_UPDATE",
            "SCHEDULED_UPDATE"
          ],
      "description" : "<p>Java class for NextVersionType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"NextVersionType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"NO_UPDATES\"/>\r\n     &lt;enumeration value=\"NON_SCHEDULED_UPDATE\"/>\r\n     &lt;enumeration value=\"SCHEDULED_UPDATE\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_NonEnumeratedDimensionValue" : {
      "type" : "object",
      "title" : "NonEnumeratedDimensionValue",
      "allOf" : [
        {
          "properties" : {
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            }
          }
        }
      ],
      "description" : "<p>Java class for NonEnumeratedDimensionValue complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"NonEnumeratedDimensionValue\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_NonEnumeratedDimensionValues" : {
      "type" : "object",
      "title" : "NonEnumeratedDimensionValues",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_stat_DimensionValues"
        },
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "value" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_NonEnumeratedDimensionValue"
            }
          }
        }
      ],
      "description" : "<p>Java class for NonEnumeratedDimensionValues complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"NonEnumeratedDimensionValues\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DimensionValues\">\r\n       &lt;sequence>\r\n         &lt;element name=\"value\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}NonEnumeratedDimensionValue\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Queries" : {
      "type" : "object",
      "title" : "Queries",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_ListBase"
        },
        {
          "properties" : {
            "query" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for Queries complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Queries\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ListBase\">\r\n       &lt;sequence>\r\n         &lt;element name=\"query\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Query" : {
      "type" : "object",
      "title" : "Query",
      "allOf" : [
        {
          "properties" : {
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "childLinks" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ChildLinks"
            },
            "data" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Data"
            },
            "description" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "metadata" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_QueryMetadata"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "parentLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "selectedLanguages" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_SelectedLanguages"
            },
            "selfLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "urn" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for Query complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Query\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"urn\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"selfLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"parentLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"childLinks\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ChildLinks\"/>\r\n         &lt;element name=\"name\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"description\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"selectedLanguages\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}SelectedLanguages\"/>\r\n         &lt;element name=\"metadata\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}QueryMetadata\" minOccurs=\"0\"/>\r\n         &lt;element name=\"data\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Data\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"kind\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_QueryMetadata" : {
      "type" : "object",
      "title" : "QueryMetadata",
      "allOf" : [
        {
          "properties" : {
            "attributes" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Attributes"
            },
            "dimensions" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_Dimensions"
            },
            "isPartOf" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "latestDataNumber" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "number"
            },
            "maintainer" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "relatedDataset" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "relatedDsd" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_DataStructureDefinition"
            },
            "requires" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "statisticalOperation" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "status" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_QueryStatus"
            },
            "type" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_QueryType"
            },
            "validFrom" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "validTo" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for QueryMetadata complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"QueryMetadata\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"relatedDataset\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"relatedDsd\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}DataStructureDefinition\"/>\r\n         &lt;element name=\"dimensions\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Dimensions\"/>\r\n         &lt;element name=\"attributes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}Attributes\" minOccurs=\"0\"/>\r\n         &lt;element name=\"status\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}QueryStatus\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}QueryType\"/>\r\n         &lt;element name=\"latestDataNumber\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n         &lt;element name=\"requires\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"isPartOf\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"statisticalOperation\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"maintainer\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"validFrom\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"validTo\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_QueryStatus" : {
      "type" : "string",
      "title" : "QueryStatus",
          "enum" : [
            "ACTIVE",
            "DISCONTINUED"
          ],
      "description" : "<p>Java class for QueryStatus.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"QueryStatus\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"ACTIVE\"/>\r\n     &lt;enumeration value=\"DISCONTINUED\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_QueryType" : {
      "type" : "string",
      "title" : "QueryType",
          "enum" : [
            "AUTOINCREMENTAL",
            "LATEST_DATA",
            "FIXED"
          ],
      "description" : "<p>Java class for QueryType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"QueryType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"AUTOINCREMENTAL\"/>\r\n     &lt;enumeration value=\"LATEST_DATA\"/>\r\n     &lt;enumeration value=\"FIXED\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_SelectedLanguages" : {
      "type" : "object",
      "title" : "SelectedLanguages",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "language" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for SelectedLanguages complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"SelectedLanguages\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"language\" type=\"{http://www.w3.org/2001/XMLSchema}string\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_StatisticalResource" : {
      "type" : "object",
      "title" : "StatisticalResource",
      "allOf" : [
        {
          "properties" : {
            "abstract" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "accessRights" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "conformsTo" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "contributors" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "copyrightDate" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "number"
            },
            "createdDate" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "creator" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "hasPart" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "isPartOf" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "isReplacedBy" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "keywords" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "language" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "languages" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "lastUpdate" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "license" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "maintainer" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "mediators" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "newnessUntilDate" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "nextVersion" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_NextVersionType"
            },
            "publisherContributors" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "publishers" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "replaces" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "rightsHolder" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "statisticalOperation" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "statisticalOperationInstances" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resources"
            },
            "subtitle" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "titleAlternative" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "type" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_StatisticalResourceType"
            },
            "validFrom" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "validTo" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "version" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "versionRationale" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "versionRationaleTypes" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_VersionRationaleTypes"
            }
          }
        }
      ],
      "description" : "<p>Java class for StatisticalResource complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"StatisticalResource\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"language\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"languages\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"statisticalOperation\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"statisticalOperationInstances\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"subtitle\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"titleAlternative\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"abstract\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"keywords\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"type\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}StatisticalResourceType\"/>\r\n         &lt;element name=\"creator\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"contributors\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"createdDate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"lastUpdate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"conformsTo\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"publishers\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"publisherContributors\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"mediators\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\" minOccurs=\"0\"/>\r\n         &lt;element name=\"newnessUntilDate\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\" minOccurs=\"0\"/>\r\n         &lt;element name=\"replaces\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"isReplacedBy\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"hasPart\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"isPartOf\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resources\"/>\r\n         &lt;element name=\"rightsHolder\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"copyrightDate\" type=\"{http://www.w3.org/2001/XMLSchema}int\" minOccurs=\"0\"/>\r\n         &lt;element name=\"license\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"accessRights\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"maintainer\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;element name=\"version\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"versionRationaleTypes\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}VersionRationaleTypes\"/>\r\n         &lt;element name=\"versionRationale\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"validFrom\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\"/>\r\n         &lt;element name=\"validTo\" type=\"{http://www.w3.org/2001/XMLSchema}dateTime\" minOccurs=\"0\"/>\r\n         &lt;element name=\"nextVersion\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}NextVersionType\"/>\r\n       &lt;/sequence>\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_StatisticalResourceType" : {
      "type" : "string",
      "title" : "StatisticalResourceType",
          "enum" : [
            "DATASET",
            "COLLECTION",
            "QUERY"
          ],
      "description" : "<p>Java class for StatisticalResourceType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"StatisticalResourceType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"DATASET\"/>\r\n     &lt;enumeration value=\"COLLECTION\"/>\r\n     &lt;enumeration value=\"QUERY\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_Table" : {
      "type" : "object",
      "title" : "Table",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_stat_CollectionNode"
        },
        {
          "properties" : {
            "dataset" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            },
            "query" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_Resource"
            }
          }
        }
      ],
      "description" : "<p>Java class for Table complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Table\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}CollectionNode\">\r\n       &lt;choice>\r\n         &lt;sequence>\r\n           &lt;element name=\"dataset\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n           &lt;element name=\"query\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\"/>\r\n         &lt;/sequence>\r\n       &lt;/choice>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_stat_VersionRationaleType" : {
      "type" : "string",
      "title" : "VersionRationaleType",
          "enum" : [
            "MAJOR_NEW_RESOURCE",
            "MAJOR_ESTIMATORS",
            "MAJOR_CATEGORIES",
            "MAJOR_VARIABLES",
            "MAJOR_OTHER",
            "MINOR_ERRATA",
            "MINOR_METADATA",
            "MINOR_DATA_UPDATE",
            "MINOR_SERIES_UPDATE",
            "MINOR_OTHER"
          ],
      "description" : "<p>Java class for VersionRationaleType.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"VersionRationaleType\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"MAJOR_NEW_RESOURCE\"/>\r\n     &lt;enumeration value=\"MAJOR_ESTIMATORS\"/>\r\n     &lt;enumeration value=\"MAJOR_CATEGORIES\"/>\r\n     &lt;enumeration value=\"MAJOR_VARIABLES\"/>\r\n     &lt;enumeration value=\"MAJOR_OTHER\"/>\r\n     &lt;enumeration value=\"MINOR_ERRATA\"/>\r\n     &lt;enumeration value=\"MINOR_METADATA\"/>\r\n     &lt;enumeration value=\"MINOR_DATA_UPDATE\"/>\r\n     &lt;enumeration value=\"MINOR_SERIES_UPDATE\"/>\r\n     &lt;enumeration value=\"MINOR_OTHER\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_stat_VersionRationaleTypes" : {
      "type" : "object",
      "title" : "VersionRationaleTypes",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "versionRationaleType" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_stat_VersionRationaleType"
            }
          }
        }
      ],
      "description" : "<p>Java class for VersionRationaleTypes complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"VersionRationaleTypes\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"versionRationaleType\" type=\"{http://www.siemac.org/metamac/rest/statistical-resources/v1.0/domain}VersionRationaleType\" maxOccurs=\"unbounded\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"total\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}unsignedLong\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
  },
  "paths": {
    "/v1.0/collections" : {
      "get" : {
        "tags" : [ "\/v1.0/collections" ],
        "description" : "",
        "operationId" : "resource__v1.0_collections_findCollections_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "limit",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "offset",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Collections"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/collections/{agencyID}" : {
      "get" : {
        "tags" : [ "\/v1.0/collections/{agencyID}" ],
        "description" : "",
        "operationId" : "resource__v1.0_collections__agencyID__findCollections_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "agencyID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "limit",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "offset",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Collections"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/collections/{agencyID}/{resourceID}" : {
      "get" : {
        "tags" : [ "\/v1.0/collections/{agencyID}/{resourceID}" ],
        "description" : "",
        "operationId" : "resource__v1.0_collections__agencyID___resourceID__retrieveCollection_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "agencyID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "resourceID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "fields",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Collection"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/datasets" : {
      "get" : {
        "tags" : [ "\/v1.0/datasets" ],
        "description" : "",
        "operationId" : "resource__v1.0_datasets_findDatasets_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "limit",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "offset",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Datasets"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/datasets/{agencyID}" : {
      "get" : {
        "tags" : [ "\/v1.0/datasets/{agencyID}" ],
        "description" : "",
        "operationId" : "resource__v1.0_datasets__agencyID__findDatasets_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "agencyID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "limit",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "offset",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Datasets"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/datasets/{agencyID}/{resourceID}" : {
      "get" : {
        "tags" : [ "\/v1.0/datasets/{agencyID}/{resourceID}" ],
        "description" : "",
        "operationId" : "resource__v1.0_datasets__agencyID___resourceID__findDatasets_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "agencyID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "resourceID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "limit",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "offset",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Datasets"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/datasets/{agencyID}/{resourceID}/{version}" : {
      "get" : {
        "tags" : [ "\/v1.0/datasets/{agencyID}/{resourceID}/{version}" ],
        "description" : "",
        "operationId" : "resource__v1.0_datasets__agencyID___resourceID___version__retrieveDataset_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "agencyID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "resourceID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "version",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "dim",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "fields",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Dataset"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/queries" : {
      "get" : {
        "tags" : [ "\/v1.0/queries" ],
        "description" : "",
        "operationId" : "resource__v1.0_queries_findQueries_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "limit",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "offset",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Queries"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/queries/{agencyID}" : {
      "get" : {
        "tags" : [ "\/v1.0/queries/{agencyID}" ],
        "description" : "",
        "operationId" : "resource__v1.0_queries__agencyID__findQueries_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "agencyID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "limit",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "offset",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Queries"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/queries/{agencyID}/{resourceID}" : {
      "get" : {
        "tags" : [ "\/v1.0/queries/{agencyID}/{resourceID}" ],
        "description" : "",
        "operationId" : "resource__v1.0_queries__agencyID___resourceID__retrieveQuery_GET",
        "produces" : [ "application/json", "application/xml" ],
        "parameters" : [
          {
            "name" : "agencyID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "resourceID",
            "in" : "path",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "fields",
            "in" : "query",
            "type" : "string",
            "description" : ""
          },
          {
            "name" : "lang",
            "in" : "query",
            "type" : "string",
            "description" : ""
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/json_Query"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
  }
}
