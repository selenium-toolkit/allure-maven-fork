/*
 *  Copyright 2021 Qameta Software OÜ
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.seleniumToolkit.allureLoader;

import io.qameta.allure.maven.AllureCommandline;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.settings.Proxy;
import org.apache.maven.shared.transfer.dependencies.resolve.DependencyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class SeleniumToolkitCommandline extends AllureCommandline {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumToolkitCommandline.class);
    public static final String TOOLKIT_REPORT_VERSION = "2.33";
    public static final String ALLURE_COMMANDLINE = "allure-commandline/allure-commandline-".concat(TOOLKIT_REPORT_VERSION).concat(".zip");

    public SeleniumToolkitCommandline(Path installationDirectory, String version) {
        super(installationDirectory, version);
    }

    public SeleniumToolkitCommandline(Path installationDirectory, String version, int timeout) {
        super(installationDirectory, version, timeout);
    }

    public void getSeleniumToolkitAllureCommandline() throws IOException {
        final Path allureZip = Files.createTempFile("allure", this.getVersion());
        LOGGER.info("Load AllureCommandline from Allure-Maven Plugin. File:"
                + SeleniumToolkitCommandline.class.getClassLoader().getResource(ALLURE_COMMANDLINE).getFile());
        final InputStream inputStream = SeleniumToolkitCommandline.class.getClassLoader().getResourceAsStream(ALLURE_COMMANDLINE);
        LOGGER.info("Copy allureZip");
        Files.copy(inputStream, allureZip, StandardCopyOption.REPLACE_EXISTING);
        LOGGER.info("unpack allureZip");
        unpack(allureZip.toFile());
    }

    @Override
    public void downloadWithMaven(MavenSession session, DependencyResolver dependencyResolver) throws IOException {
        getSeleniumToolkitAllureCommandline();
    }

    @Override
    public void download(String allureDownloadUrl, Proxy mavenProxy) throws IOException {
        getSeleniumToolkitAllureCommandline();
    }
}
