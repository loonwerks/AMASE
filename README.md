# AMASE
This is the repository for Architectural Modeling and Analysis for Safety Engineering (AMASE). 

![master](https://github.com/loonwerks/AMASE/actions/workflows/maven.yml/badge.svg)

Directories:
-----------
	doc : For published papers, the users guide, and other documentation. 
	examples: For all AADL/AGREE/Safety Annex examples used in the project. 
			  See README_Files in the examples directory for more info.
	safety_annex: The complete source code for the Safety Annex plugin
	safety_update_site: The link to the update site for plugin download.

## Development Guide

### Building in Tycho-Maven

[Tycho](https://www.eclipse.org/tycho/) is a collection of
[Maven](https://maven.apache.org/) plugins that map the Eclipse builds
into Maven.  Principally, it maps the OSGi dependencies into maven
dependencies.  Accordingly, the contents of the AMASE plugin manifests
are combined with the pom.xml configurations to produce a unified
builod environment.

To build and package AMASE as an Eclipse P2 repository change directories
to safety_annex/plugins and apply the command line

`mvn clean verify`

This command deletes generated files (except Xtext artifacts and
documenation outputs), compiles, runs tests and completes the
packaging.

### Releases (Updated!)

Since AMASE has converted to the [Semantic
Versioning](https://semver.org/) model, each of the plugins that
compose the AMASE feature now have
individual version numbers that reflect the updates to that plugin.
Thus, they are no longer synchronized to the feature versions.

The AMASE code base has been modified to enable Eclipse plugin API
analysis which will result in error messages and warnings (in the
"Problems" tab of the IDE) to guide developers as to how and when to
update version numbers and apply API tags such as @since to added or
modified elements.  The Oomph version management analysis has also
been enabled to assist with version number markings.

The process for a release updates master branch with the release
version number, commits the master branch version number updates,
builds the release version, updates the version numbering to the new
development snapshot numbers, and finally commits the master branch
with the new development version numbering.  To accomplish the release
preparation, the AGREE release process applies the [Tycho Release
Workflow](https://wiki.eclipse.org/Tycho/Release_Workflow) to help
automate the process.  The steps in the release workflow are as
follows:

1. Prepare for the release by running the release workflow plugin:

   `mvn release:prepare`

   During this process maven will ask to confirm all of the plugin and
   feature versions and for the tag to applied to the
   release. Release tags shall be of the form 'x.y.z-RELEASE' where
   `x` is the major version number, `y` is the minor version number,
   and `z` is the patch version number.

1. Ordinarily one would expect to actually perform the release using
   the customary `mvn release:perform -Dgoals="clean verify"` command.
   However, this appears to run into difficulty fetching the appropriate
   tag to build.  Instead do the following:

   `git checkout x.x.x-RELEASE`
   `mvn clean verify`

   where `x.x.x` is the version number to be released.

1. Commit the release binaries into the releases directory of the repository at
   git@github.com:loonwerks/AMASE/safety-update-site:

   `cp -r <path to built AMASE release>/repository/target/repository agree-<release-version-number>`

   Update the contents of the compositeArtifacts.xml and compositeContent.xml files.

   `git add ...`

   `git commit -m "Add AMASE <release-version-number>"`

1. Finally, push the update stable branch to the origin repository:

   `git push`

Note that the previous instructions assume that the developer has set
up appropriate ssh keys such that interactive query for authentication
is necessary.  If this is not done, the `release:prepare` and
`release:perform` steps will apparently just hang when such
interaction is required as they are in fact headless operations.
Alternatively to setting up ssh keys, the developer may add necessary
authenication information to the maven settings as described in [Tycho
Release Workflow -- Configure the
SCM](https://wiki.eclipse.org/Tycho/Release_Workflow#Configure_the_SCM).
