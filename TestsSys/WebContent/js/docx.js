/*
 * DOCX.js - Generate .docx files using pure client-side JavaScript.
 */

var DOCXjs = function () {

    var parts = {};

    // Content store
    var textElements = [];


    var documentGen = function () {

        // Headers
        //var output = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><w:document mc:ignorable="w14" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup" xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"><w:body>';
        //
        //// Paragraphs
        //for (var textElement in textElements) {
        //    output += textElements[textElement];
        //}
        //
        //// Bottom section
        //output += '<w:sectpr><w:headerreference r:id="rId4" w:type="default"></w:headerreference><w:footerreference r:id="rId5" w:type="default"></w:footerreference><w:pgsz w:h="15840" w:orient="portrait" w:w="12240"></w:pgsz><w:pgmar w:bottom="1440" w:footer="720" w:header="720" w:left="1800" w:right="1800" w:top="1440"></w:pgmar><w:bidi w:val="0"></w:bidi></w:sectpr>';
        //
        //// Close
        //output += '</w:body></w:document>';

        return textElements[0];
    }


    var generate = function () {
        // Content types

        var files = [
            '[Content_Types].xml',
            '_rels/.rels',
            'docProps/app.xml',
            'docProps/core.xml',
            'word/_rels/document.xml.rels',
            'word/document.xml',
            'word/fontTable.xml',
            'word/footer1.xml',
            'word/header1.xml',
            'word/settings.xml',
            'word/styles.xml',
            'word/theme/theme1.xml'
        ];
        var file_data = {};

        var file_count = files.length;
        var file_count_current = 0;
        var zip = new JSZip("STORE");

        var doOutput = function () {
            outputFile = zip.generate();
            document.location.href = 'data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,' + outputFile;
        }

        for (var file in files) {
            console.log(file);
            if (files[file] == 'word/document.xml') {
                zip.add('word/document.xml', documentGen());
                file_count_current++;

                if (file_count == file_count_current) {
                    doOutput();
                }
            } else {
                $.ajax({
                    url: 'blank/' + files[file],
                    complete: function (r) {
                        zip.add(this.url.replace('blank/', ''), r.responseText);
                        file_count_current++;

                        if (file_count == file_count_current) {
                            doOutput();
                        }
                    }
                });
            }

        }

        return parts;

    }


    // Add content methods

    var addText = function (string) {
        textElements.push(string);
    }

    var finalFile = function (parts) {
        var zip = new JSZip();
        for (var part in parts) {
            zip.add(part, parts[part]);
        }
        return zip.generate();
    }

    return {
        output: function (type, options) {

            var buffer = generate();
            return;
            if (type == undefined) {

                return buffer;
            }
            if (type == 'datauri') {
                var outputFile = finalFile(buffer);
                document.location.href = 'data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,' + outputFile;
            }
            // @TODO: Add different output options
        },
        text: function (string) {
            addText(string);
        }
    };

};