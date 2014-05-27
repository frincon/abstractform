Introudction
---------------

In this tutorial you will define simple form for show and edit a bean and render it in vaadin 6.

Bean to Edit
------------------------

The bean we want to edit is the following class __`BusinessPartner.java`__ 

	public class BusinessPartner {

		public static final String PROPERTY_SEARCH_KEY = "searchKey";
		public static final String PROPERTY_ACTIVE = "active";
		public static final String PROPERTY_NAME = "name";

		private String searchKey;
		private boolean active;
		private String name;

		public String getSearchKey() {
			return searchKey;
		}

		public void setSearchKey(String searchKey) {
			this.searchKey = searchKey;
		}

		public boolean isActive() {
			return active;
		}
	
		public void setActive(boolean active) {
			this.active = active;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}

	}
	
Definition of the form
----------------------

You need to define form with one of the ways that the AbstractForm framework has. At this moment, the only ay to define form is 
with the fluent api plugin, but in the future is planned to make other implementations to define form (like XML, database).

You can extend the class `BeanBasedBFForm` like this:

	public class SampleForm extends BeanBasedBFForm<BusinessPartner> {
	
	}
	
The generic parameter `BusinessPartner` indicate that the model of this form is an object of this class.

Then, you need to define the structure of the form. You can do this using the fluent api like this:

		public final BFSubForm SF_MAIN = addSubForm("sfMain", 2);
	
		public final BFField F_SEARCH_KEY = SF_MAIN.addField(0, 0, "fSearchKey", "Search Key", BusinessPartner.PROPERTY_SEARCH_KEY)
				.description("Search key")
				.displayWidth(40)
				.maxLength(40)
				.readOnly(false)
				.required(true1);
		public final BFField F_ACTIVE = SF_MAIN.addField(0, 1, "fActive", "Active", BusinessPartner.PROPERTY_ACTIVE)
				.description("The active field")
				.displayWidth(40)
				.readOnly(false)
				.required(true);
		public final BFField F_NAME = SF_MAIN.addField(1, 0, "fName", "Name", BusinessPartner.PROPERTY_NAME)
				.description("The name")
				.displayWidth(60)
				.maxLength(60)
				.readOnly(false)
				.required(true);

The first line define a sub form with the id `sfMain` (its not necessary to establish an id explicitly but is a good practce, 
if you don't establish them, the framework assign an unique id), and with 2 columns for the fields that contains.

The SubForm is the unique container that can contains fields. There are multiple containers in the framework: `Drawer`, `Section`,
`TabSheet` and `SubForm`.

The next three statements add the fields to edit the bean. The fluent API is self explained in these lines.

Then simply you need to create constructor for the form definition:

		public SampleForm() {
			super("SampleForm", "The sample form", BusinessPartner.class);
		}
	
Using the form definition
-------------------------

When you have a form definition, you can use this definition to build the form in one of the supported APIs. At this time, the only
api that is supported is Vaadin, but is planned to support others like SWT, Swing, Lanterna.

In this example we are going to use vaadin plugins to render and bind this form. This example uses Vaadin version 6.

Then first you need is a Vaadin application:

	public class SampleApplication extends Application {

	}
	
And in method `init()` we are going to render the form and bind it to a bean object.

		@Override
		public void init() {
	
			// Create main window of the application
			Window main = new Window("Test window");
			setMainWindow(main);
			
			//Get the Binding Form Toolkit that create components (render the form as a component of vaadin)
			BFormToolkit<Component> toolkit = BFormService.getInstance().getFormToolkit(Component.class); 
			
			//Create a sample form definition instance 
			BForm<BusinessPartner> form = new SampleForm();
			
			//Build the form with the toolkit and add the component to the window
			final BFormInstance<BusinessPartner, Component> formInstance = toolkit.buildForm(form);
			main.addComponent(formInstance.getImplementation());
			
			//Create sample bean
			final BusinessPartner bean1 = new BusinessPartner();
			bean1.setActive(true);
			bean1.setSearchKey("KEY");
			bean1.setName("Sample Name");
			
			//Establish the bean as a model of the form instance than we created before
			formInstance.setValue(bean1);
			
		}

More complex examples
---------------------

More complex sample application can be found on the sources of AbstractForm in the __org.abstractform.sampleapp__ directory. This
application has validators, tables, selectors and other features not covered by this tutorial.

