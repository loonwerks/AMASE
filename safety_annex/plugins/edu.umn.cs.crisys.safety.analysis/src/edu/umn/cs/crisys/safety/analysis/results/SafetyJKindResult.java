package edu.umn.cs.crisys.safety.analysis.results;

import static java.util.stream.Collectors.toList;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.api.results.JKindResult;
import jkind.api.results.MultiStatus;
import jkind.api.results.PropertyResult;
import jkind.api.results.Renaming;
import jkind.api.results.Status;
import jkind.api.results.Ticker;
import jkind.excel.ExcelFormatter;
import jkind.results.Property;
import jkind.results.layout.Layout;
import jkind.results.layout.SingletonLayout;

public class SafetyJKindResult extends JKindResult {

	private String text;
	private final List<PropertyResult> propertyResults = new ArrayList<>();
	private final MultiStatus multiStatus = new MultiStatus();
	private Ticker ticker;
	private Renaming renaming;

	/**
	 * Construct an empty JKindResult to hold the results of a run of JKind
	 *
	 * @param name
	 *            Name of the results
	 */
	public SafetyJKindResult(String name) {
		super(name);
	}

	/**
	 * Construct a JKindResult to hold the results of a run of JKind
	 *
	 * @param name
	 *            Name of the results
	 * @param properties
	 *            Property names to track
	 */
	public SafetyJKindResult(String name, List<String> properties) {
		super(name);
		addProperties(properties);
	}

	/**
	 * Construct a JKindResult to hold the results of a run of JKind
	 *
	 * @param name
	 *            Name of the results
	 * @param renaming
	 *            Renaming to apply to properties
	 */
	public SafetyJKindResult(String name, Renaming renaming) {
		super(name);
		this.renaming = renaming;
	}

	/**
	 * Construct a JKindResult to hold the results of a run of JKind
	 *
	 * @param name
	 *            Name of the results
	 * @param properties
	 *            Property names to track (pre-renaming)
	 * @param renaming
	 *            Renaming to apply to properties
	 */
	public SafetyJKindResult(String name, List<String> properties, Renaming renaming) {
		super(name);
		this.renaming = renaming;
		addProperties(properties);
	}

	/**
	 * Construct a JKindResult to hold the results of a run of JKind
	 *
	 * @param name
	 *            Name of the results
	 * @param properties
	 *            Property names to track (pre-renaming)
	 * @param invertStatus
	 *            True if the status of the property of the same index in
	 *            properties should be inverted
	 */
	public SafetyJKindResult(String name, List<String> properties, List<Boolean> invertStatus) {
		super(name);
		addProperties(properties, invertStatus);
	}

	/**
	 * Construct a JKindResult to hold the results of a run of JKind
	 *
	 * @param name
	 *            Name of the results
	 * @param properties
	 *            Property names to track (pre-renaming)
	 * @param invertStatus
	 *            True if the status of the property of the same index in
	 *            properties should be inverted
	 * @param renaming
	 *            Renaming to apply to properties
	 */
	public SafetyJKindResult(String name, List<String> properties, List<Boolean> invertStatus, Renaming renaming) {
		super(name);
		this.renaming = renaming;
		addProperties(properties, invertStatus);
	}

	private void addProperties(List<String> properties, List<Boolean> invertStatus) {
		int i = 0;
		if (properties.size() != invertStatus.size()) {
			throw new IllegalArgumentException("Lists have different length");
		}
		for (String property : properties) {
			addProperty(property, invertStatus.get(i++));
		}
	}

	/**
	 * Add a new property to track
	 *
	 * @param property
	 *            Property to be tracked (pre-renaming)
	 * @param invertStatus
	 *            True if finding a model means success. Otherwise false.
	 * @return The PropertyResult object which will store the results of the
	 *         property
	 */
	@Override
	public PropertyResult addProperty(String property, boolean invertStatus) {
		if (renaming != null) {
			property = renaming.rename(property);
			if (property == null) {
				return null;
			}
		}

		SafetyPropertyResult propertyResult = new SafetyPropertyResult(property, renaming, invertStatus);
		propertyResults.add(propertyResult);
		propertyResult.setParent(this);
		pcs.fireIndexedPropertyChange("propertyResults", propertyResults.size() - 1, null, propertyResult);
		addStatus(propertyResult.getStatus());
		propertyResult.addPropertyChangeListener(this);
		return propertyResult;
	}

	/**
	 * Add a new property to track
	 *
	 * @param property
	 *            Property to be tracked (pre-renaming)
	 * @return The PropertyResult object which will store the results of the
	 *         property
	 */
	@Override
	public PropertyResult addProperty(String property) {
		return addProperty(property, false);
	}

	/**
	 * Add a property to track (with inverted status)
	 *
	 * @param property
	 *            Property to be tracked (pre-renaming)
	 * @return The PropertyResult object which will store the results of the
	 *         property
	 */
	@Override
	public PropertyResult addInvertedProperty(String property) {
		return addProperty(property, true);
	}

	/**
	 * Add a new properties to track
	 *
	 * @param properties
	 *            Properties to be tracked (pre-renaming)
	 * @return List of the PropertyResult objects which will store the results
	 *         of the properties
	 */
	@Override
	public List<PropertyResult> addProperties(List<String> properties) {
		return properties.stream().map(this::addProperty).collect(toList());
	}

	/**
	 * Add a new properties to track (with inverted status)
	 *
	 * @param properties
	 *            Properties to be tracked (pre-renaming)
	 * @return List of the PropertyResult objects which will store the results
	 *         of the properties
	 */
	@Override
	public List<PropertyResult> addInvertedProperties(List<String> properties) {
		return properties.stream().map(this::addInvertedProperty).collect(toList());
	}

	private void addStatus(Status other) {
		multiStatus.add(other);
		pcs.firePropertyChange("status", null, other);
	}

	/**
	 * Get all PropertyResult objects stored in the JKindResult
	 */
	@Override
	public List<PropertyResult> getPropertyResults() {
		return Collections.unmodifiableList(propertyResults);
	}

	/**
	 * Get a specific PropertyResult by property name
	 *
	 * @param name
	 *            Name of property to retrieve (pre-renaming)
	 * @return Property with the given name or <code>null</code> if not found
	 */
	@Override
	public PropertyResult getPropertyResult(String name) {
		if (renaming != null) {
			name = renaming.rename(name);
			if (name == null) {
				return null;
			}
		}

		for (PropertyResult pr : propertyResults) {
			if (pr.getName().equals(name)) {
				return pr;
			}
		}
		return null;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public MultiStatus getMultiStatus() {
		return multiStatus;
	}

	@Override
	public void start() {
		for (PropertyResult pr : propertyResults) {
			pr.start();
		}
		ticker = new Ticker(this);
		ticker.start();
	}

	@Override
	public void tick() {
		for (PropertyResult pr : propertyResults) {
			pr.tick();
		}
	}

	@Override
	public void cancel() {
		for (PropertyResult pr : propertyResults) {
			pr.cancel();
		}
		if (ticker != null) {
			ticker.done();
		}
	}

	@Override
	public void done() {
		for (PropertyResult pr : propertyResults) {
			pr.done();
		}
		if (ticker != null) {
			ticker.done();
		}
	}

	@Override
	public void setBaseProgress(int k) {
		for (PropertyResult pr : propertyResults) {
			pr.setBaseProgress(k);
		}
	}

	/**
	 * Convert results to an Excel spreadsheet
	 *
	 * Using this requires the jxl.jar file in your classpath
	 *
	 * @param file
	 *            File to write Excel spreadsheet to
	 * @param layout
	 *            Layout information for counterexamples, defined over renamed
	 *            signals
	 * @see Layout
	 * @throws jkind.JKindException
	 */
	@Override
	public void toExcel(File file, Layout layout) {
		try (ExcelFormatter formatter = new ExcelFormatter(file, layout)) {
			formatter.write(getProperties());
		}
	}

	private List<Property> getProperties() {
		List<Property> properties = new ArrayList<>();
		for (PropertyResult pr : propertyResults) {
			if (pr.getProperty() != null) {
				properties.add(pr.getProperty());
			}
		}
		return properties;
	}

	/**
	 * Convert results to an Excel spreadsheet using default layout
	 *
	 * Using this requires the jxl.jar file in your classpath
	 *
	 * @param file
	 *            File to write Excel spreadsheet to
	 * @throws jkind.JKindException
	 */
	@Override
	public void toExcel(File file) {
		toExcel(file, new SingletonLayout("Signals"));
	}

	/**
	 * Discard details such as counterexamples and IVCs to save space
	 */
	@Override
	public void discardDetails() {
		for (PropertyResult pr : propertyResults) {
			if (pr.getProperty() != null) {
				pr.getProperty().discardDetails();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Status updates from immediate children are noted and propagated
		if ("status".equals(evt.getPropertyName()) && propertyResults.contains(evt.getSource())) {
			multiStatus.remove((Status) evt.getOldValue());
			multiStatus.add((Status) evt.getNewValue());
			pcs.firePropertyChange("status", evt.getOldValue(), evt.getNewValue());
		}

		if ("multiStatus".equals(evt.getPropertyName()) && propertyResults.contains(evt.getSource())) {
			multiStatus.remove((MultiStatus) evt.getOldValue());
			multiStatus.add((MultiStatus) evt.getNewValue());
			pcs.firePropertyChange("multiStatus", evt.getOldValue(), evt.getNewValue());
		}
	}

	@Override
	public String toString() {
		return name + propertyResults;
	}

	public Renaming getRenaming() {
		return renaming;
	}

}
