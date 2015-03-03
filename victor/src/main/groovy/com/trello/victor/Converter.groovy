/*
 * Copyright 2015 Trello, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trello.victor

import org.apache.batik.transcoder.Transcoder
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.ImageTranscoder
import org.apache.batik.transcoder.image.PNGTranscoder

/**
 * Converts SVGs to PNGs.
 *
 * This is split out into its own class to make it easier to test (since it doesn't require
 * any of the Task architecture).
 */
class Converter  {

    private Transcoder transcoder = new PNGTranscoder()

    void transcode(SVGResource svgResource, Density density, File destination) {
        int outWidth = Math.round(svgResource.width * density.multiplier)
        int outHeight = Math.round(svgResource.height * density.multiplier)
        transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, new Float(outWidth))
        transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, new Float(outHeight))

        String svgURI = svgResource.file.toURI().toString();
        TranscoderInput input = new TranscoderInput(svgURI);

        OutputStream outStream = new FileOutputStream(destination)
        TranscoderOutput output = new TranscoderOutput(outStream);

        transcoder.transcode(input, output);

        outStream.flush();
        outStream.close();
    }
}