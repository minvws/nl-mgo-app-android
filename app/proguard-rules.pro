# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# hapi libs
-keep class ca.uhn.fhir.** { *; }
-keep class org.hl7.fhir.dstu3.hapi.ctx.*  { *; }
-keep class org.hl7.fhir.dstu3.**  { *; }
-keep class org.hl7.fhir.utilities.**  { *; }
-keep class org.hl7.fhir.exceptions.*  { *; }
-keep class org.hl7.fhir.instance.model.api.*  { *; }

# Used by HapiWorkerContext (fhirpath engine in QuestionnaireViewModel)
-keep class com.github.benmanes.caffeine.cache.**  { *; }

# sqlcipher
-keep class net.sqlcipher.** { *; }
#-keep class net.sqlcipher.database.** { *; }

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn ca.uhn.fhir.rest.api.server.IFhirVersionServer
-dontwarn ca.uhn.fhir.rest.api.server.RequestDetails
-dontwarn ca.uhn.fhir.rest.server.Bindings
-dontwarn ca.uhn.fhir.rest.server.IServerAddressStrategy
-dontwarn ca.uhn.fhir.rest.server.IServerConformanceProvider
-dontwarn ca.uhn.fhir.rest.server.ResourceBinding
-dontwarn ca.uhn.fhir.rest.server.RestfulServer
-dontwarn ca.uhn.fhir.rest.server.RestfulServerConfiguration
-dontwarn ca.uhn.fhir.rest.server.RestfulServerUtils
-dontwarn ca.uhn.fhir.rest.server.method.BaseMethodBinding
-dontwarn ca.uhn.fhir.rest.server.method.IParameter
-dontwarn ca.uhn.fhir.rest.server.method.OperationMethodBinding$ReturnType
-dontwarn ca.uhn.fhir.rest.server.method.OperationMethodBinding
-dontwarn ca.uhn.fhir.rest.server.method.OperationParameter
-dontwarn ca.uhn.fhir.rest.server.method.SearchMethodBinding
-dontwarn ca.uhn.fhir.rest.server.method.SearchParameter
-dontwarn ca.uhn.fhir.rest.server.util.BaseServerCapabilityStatementProvider
-dontwarn com.ctc.wstx.stax.WstxInputFactory
-dontwarn com.ctc.wstx.stax.WstxOutputFactory
-dontwarn com.github.rjeschke.txtmark.Processor
-dontwarn com.google.auto.value.AutoValue
-dontwarn com.google.gson.Gson
-dontwarn com.google.gson.GsonBuilder
-dontwarn com.google.gson.JsonArray
-dontwarn com.google.gson.JsonElement
-dontwarn com.google.gson.JsonNull
-dontwarn com.google.gson.JsonObject
-dontwarn com.google.gson.JsonParser
-dontwarn com.google.gson.JsonPrimitive
-dontwarn com.google.gson.stream.JsonWriter
-dontwarn com.helger.commons.error.IError
-dontwarn com.helger.commons.error.list.IErrorList
-dontwarn com.helger.commons.io.resource.ClassPathResource
-dontwarn com.helger.commons.io.resource.IReadableResource
-dontwarn com.helger.commons.location.ILocation
-dontwarn com.helger.schematron.ISchematronResource
-dontwarn com.helger.schematron.SchematronHelper
-dontwarn com.helger.schematron.sch.SchematronResourceSCH
-dontwarn com.helger.schematron.svrl.jaxb.SchematronOutputType
-dontwarn jakarta.servlet.ServletContext
-dontwarn jakarta.servlet.http.HttpServletRequest
-dontwarn java.awt.Color
-dontwarn java.awt.image.BufferedImage
-dontwarn java.awt.image.RenderedImage
-dontwarn javax.imageio.ImageIO
-dontwarn javax.xml.crypto.dsig.CanonicalizationMethod
-dontwarn javax.xml.crypto.dsig.DigestMethod
-dontwarn javax.xml.crypto.dsig.Reference
-dontwarn javax.xml.crypto.dsig.SignatureMethod
-dontwarn javax.xml.crypto.dsig.SignedInfo
-dontwarn javax.xml.crypto.dsig.Transform
-dontwarn javax.xml.crypto.dsig.XMLSignContext
-dontwarn javax.xml.crypto.dsig.XMLSignature
-dontwarn javax.xml.crypto.dsig.XMLSignatureFactory
-dontwarn javax.xml.crypto.dsig.dom.DOMSignContext
-dontwarn javax.xml.crypto.dsig.keyinfo.KeyInfo
-dontwarn javax.xml.crypto.dsig.keyinfo.KeyInfoFactory
-dontwarn javax.xml.crypto.dsig.keyinfo.KeyValue
-dontwarn javax.xml.crypto.dsig.spec.C14NMethodParameterSpec
-dontwarn javax.xml.crypto.dsig.spec.DigestMethodParameterSpec
-dontwarn javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec
-dontwarn javax.xml.crypto.dsig.spec.TransformParameterSpec
-dontwarn javax.xml.stream.FactoryConfigurationError
-dontwarn javax.xml.stream.Location
-dontwarn javax.xml.stream.XMLEventReader
-dontwarn javax.xml.stream.XMLEventWriter
-dontwarn javax.xml.stream.XMLInputFactory
-dontwarn javax.xml.stream.XMLOutputFactory
-dontwarn javax.xml.stream.XMLResolver
-dontwarn javax.xml.stream.XMLStreamException
-dontwarn javax.xml.stream.XMLStreamWriter
-dontwarn javax.xml.stream.events.Attribute
-dontwarn javax.xml.stream.events.Characters
-dontwarn javax.xml.stream.events.Comment
-dontwarn javax.xml.stream.events.EntityReference
-dontwarn javax.xml.stream.events.Namespace
-dontwarn javax.xml.stream.events.StartElement
-dontwarn javax.xml.stream.events.XMLEvent
-dontwarn net.sf.saxon.TransformerFactoryImpl
-dontwarn okhttp3.Authenticator
-dontwarn okhttp3.Call
-dontwarn okhttp3.Credentials
-dontwarn okhttp3.Headers$Builder
-dontwarn okhttp3.Headers
-dontwarn okhttp3.HttpUrl
-dontwarn okhttp3.Interceptor$Chain
-dontwarn okhttp3.Interceptor
-dontwarn okhttp3.MediaType
-dontwarn okhttp3.OkHttpClient$Builder
-dontwarn okhttp3.OkHttpClient
-dontwarn okhttp3.Request$Builder
-dontwarn okhttp3.Request
-dontwarn okhttp3.RequestBody
-dontwarn okhttp3.Response
-dontwarn okhttp3.ResponseBody
-dontwarn okhttp3.Route
-dontwarn okhttp3.internal.http2.Header
-dontwarn okio.ByteString
-dontwarn org.apache.commons.compress.archivers.ArchiveEntry
-dontwarn org.apache.commons.compress.archivers.tar.TarArchiveEntry
-dontwarn org.apache.commons.compress.archivers.tar.TarArchiveInputStream
-dontwarn org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
-dontwarn org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
-dontwarn org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
-dontwarn org.apache.commons.compress.compressors.gzip.GzipParameters
-dontwarn org.apache.http.client.utils.URIBuilder
-dontwarn org.apache.jena.datatypes.RDFDatatype
-dontwarn org.apache.jena.datatypes.xsd.XSDDatatype
-dontwarn org.apache.jena.irix.IRIs
-dontwarn org.apache.jena.rdf.model.Literal
-dontwarn org.apache.jena.rdf.model.Model
-dontwarn org.apache.jena.rdf.model.ModelFactory
-dontwarn org.apache.jena.rdf.model.Property
-dontwarn org.apache.jena.rdf.model.RDFNode
-dontwarn org.apache.jena.rdf.model.Resource
-dontwarn org.apache.jena.rdf.model.Statement
-dontwarn org.apache.jena.rdf.model.StmtIterator
-dontwarn org.apache.jena.riot.Lang
-dontwarn org.apache.jena.riot.RDFDataMgr
-dontwarn org.apache.jena.vocabulary.RDF
-dontwarn org.codehaus.stax2.io.EscapingWriterFactory
-dontwarn org.commonmark.Extension
-dontwarn org.commonmark.ext.gfm.tables.TablesExtension
-dontwarn org.commonmark.node.Node
-dontwarn org.commonmark.parser.Parser$Builder
-dontwarn org.commonmark.parser.Parser
-dontwarn org.commonmark.renderer.html.HtmlRenderer$Builder
-dontwarn org.commonmark.renderer.html.HtmlRenderer
-dontwarn org.fhir.ucum.Decimal
-dontwarn org.fhir.ucum.UcumException
-dontwarn org.junit.platform.engine.Filter
-dontwarn org.junit.platform.engine.TestExecutionResult$Status
-dontwarn org.junit.platform.engine.TestExecutionResult
-dontwarn org.junit.platform.engine.discovery.ClassNameFilter
-dontwarn org.junit.platform.engine.discovery.DiscoverySelectors
-dontwarn org.junit.platform.engine.discovery.PackageSelector
-dontwarn org.junit.platform.launcher.Launcher
-dontwarn org.junit.platform.launcher.LauncherDiscoveryRequest
-dontwarn org.junit.platform.launcher.LauncherSession
-dontwarn org.junit.platform.launcher.TestExecutionListener
-dontwarn org.junit.platform.launcher.TestIdentifier
-dontwarn org.junit.platform.launcher.TestPlan
-dontwarn org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
-dontwarn org.junit.platform.launcher.core.LauncherFactory
-dontwarn org.junit.platform.launcher.listeners.SummaryGeneratingListener
-dontwarn org.junit.platform.launcher.listeners.TestExecutionSummary$Failure
-dontwarn org.junit.platform.launcher.listeners.TestExecutionSummary
-dontwarn org.junit.runner.Description
-dontwarn org.junit.runner.JUnitCore
-dontwarn org.junit.runner.Result
-dontwarn org.junit.runner.notification.Failure
-dontwarn org.junit.runner.notification.RunListener
-dontwarn org.stringtemplate.v4.ST
-dontwarn org.thymeleaf.IEngineConfiguration
-dontwarn org.thymeleaf.TemplateEngine
-dontwarn org.thymeleaf.cache.AlwaysValidCacheEntryValidity
-dontwarn org.thymeleaf.cache.ICacheEntryValidity
-dontwarn org.thymeleaf.context.Context
-dontwarn org.thymeleaf.context.IContext
-dontwarn org.thymeleaf.context.IExpressionContext
-dontwarn org.thymeleaf.context.ITemplateContext
-dontwarn org.thymeleaf.dialect.IDialect
-dontwarn org.thymeleaf.dialect.IExpressionObjectDialect
-dontwarn org.thymeleaf.engine.AttributeName
-dontwarn org.thymeleaf.expression.IExpressionObjectFactory
-dontwarn org.thymeleaf.messageresolver.IMessageResolver
-dontwarn org.thymeleaf.model.IProcessableElementTag
-dontwarn org.thymeleaf.processor.element.AbstractAttributeTagProcessor
-dontwarn org.thymeleaf.processor.element.AbstractElementTagProcessor
-dontwarn org.thymeleaf.processor.element.IElementTagStructureHandler
-dontwarn org.thymeleaf.standard.StandardDialect
-dontwarn org.thymeleaf.standard.expression.IStandardExpression
-dontwarn org.thymeleaf.standard.expression.IStandardExpressionParser
-dontwarn org.thymeleaf.standard.expression.StandardExpressions
-dontwarn org.thymeleaf.templatemode.TemplateMode
-dontwarn org.thymeleaf.templateresolver.DefaultTemplateResolver
-dontwarn org.thymeleaf.templateresolver.ITemplateResolver
-dontwarn org.thymeleaf.templateresource.ITemplateResource
-dontwarn org.thymeleaf.templateresource.StringTemplateResource
-dontwarn org.thymeleaf.util.Validate
-dontwarn org.w3c.dom.events.Event
-dontwarn org.w3c.dom.events.EventListener
-dontwarn org.w3c.dom.events.EventTarget
-dontwarn org.w3c.dom.events.MutationEvent
-dontwarn com.google.j2objc.annotations.RetainedWith
-dontwarn com.google.j2objc.annotations.Weak
