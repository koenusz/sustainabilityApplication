package nl.mycompany.webapp.ui.questionairebuilder;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.abstracts.ViewEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.Tree.TreeTargetDetails;

@SuppressWarnings("serial")
@SpringView(name = QuestionaireBuilderView.VIEW_NAME)
public class QuestionaireBuilderViewImpl extends HorizontalLayout implements
		QuestionaireBuilderView {

	private static final Logger LOG = Logger
			.getLogger(QuestionaireBuilderViewImpl.class);

	@Autowired
	private transient QuestionaireBuilderPresenter presenter;

	private Tree tree;
	private Table table;

	@PostConstruct
	void init() {
		presenter.setView(this);
		setSpacing(true);

		// First create the components to be able to refer to them as allowed
		// drag sources
		tree = new Tree("The questionaire contends");
		table = new Table("Available Questions");
		table.setWidth("100%");

		// Populate the tree and set up drag & drop
		initializeTree(new SourceIs(table));

		// Populate the table and set up drag & drop
		initializeTable(new SourceIs(tree));

		// Add components
		addComponent(tree);
		addComponent(table);
	}

	private void initializeTree(final ClientSideCriterion acceptCriterion) {
		tree.setContainerDataSource(presenter.getTreeData());
		tree.setItemCaptionPropertyId(QuestionaireBuilderPresenter.NAME);

		// Expand all nodes
		for (Iterator<?> it = tree.rootItemIds().iterator(); it.hasNext();) {
			tree.expandItemsRecursively(it.next());
		}
		tree.setDragMode(TreeDragMode.NODE);

		tree.setDropHandler(new DropHandler() {
			private static final long serialVersionUID = 7492520500678755519L;

			public AcceptCriterion getAcceptCriterion() {
				// Accept drops in the middle of container items
				// and below and above all items.
				return AcceptItem.ALL;
			}

			@SuppressWarnings("unchecked")
			public void drop(DragAndDropEvent event) {
				// Wrapper for the object that is dragged
				DataBoundTransferable t = (DataBoundTransferable) event
						.getTransferable();

				TreeTargetDetails target = (TreeTargetDetails) event
						.getTargetDetails();

				// Get ids of the dragged item and the target item
				Object sourceItemId = t.getData("itemId");
				Object targetItemId = target.getItemIdOver();

				// On which side of the target the item was dropped
				VerticalDropLocation location = target.getDropLocation();

				//if the source is not a question dragging is not allowed
				BeanItem<?> beanItem = null;
				if (sourceItemId instanceof Question)
					beanItem = new BeanItem<Question>((Question) sourceItemId);
				else
					return;

				// Remove the item from the source container and
				// add it to the tree's container
				Container sourceContainer = t.getSourceContainer();
				sourceContainer.removeItem(sourceItemId);
				Question bean = (Question) beanItem.getBean();

				Item targetBean = tree.addItem(bean);
				targetBean.getItemProperty(QuestionaireBuilderPresenter.NAME)
						.setValue("" + bean.getId());
				tree.setChildrenAllowed(bean, false);

				// get the domain

				String domain = bean.getDomain();

				// Drop right on an item -> make it a child
				// if the domain is not present, create a new domain in the tree
				// and drop the question on it as a child.
				// else issue a warning

				// add a new domain if nescesary, ignored if already present
				Item newDomain = tree.addItem(domain);
				if (newDomain != null) {
					newDomain
							.getItemProperty(QuestionaireBuilderPresenter.NAME)
							.setValue(domain);
					tree.setChildrenAllowed(domain, true);
					tree.expandItem(domain);
				}
				tree.setParent(bean, domain);
				tree.setItemCaption(bean, "" + bean.getId());
				tree.setChildrenAllowed(domain, true);

				HierarchicalContainer container = (HierarchicalContainer) tree
						.getContainerDataSource();
				
				Object sourceParentId = container.getParent(sourceItemId);
				Object targetParentId = container.getParent(targetItemId);

				//moving not allowed between domains
				if(sourceParentId.equals(targetParentId))
				{
				// Drop at the top of a subtree -> make it previous
				if (location == VerticalDropLocation.TOP) {

					tree.setParent(bean, targetParentId);
					container.moveAfterSibling(bean, targetItemId);
					container.moveAfterSibling(targetItemId, bean);
				}

				// Drop below another item -> make it next
				else if (location == VerticalDropLocation.BOTTOM) {

					tree.setParent(bean, targetParentId);
					container.moveAfterSibling(bean, targetItemId);
					// reattach it to the parent, when mouseovering over the
					// root it and then dropping inbetween i wil count as below
					// the rootitem, thus outside the container
					if (targetItemId.equals(domain)) {
						tree.setParent(bean, targetParentId);
					}
				}

			}}
		});
	}

	private void initializeTable(final ClientSideCriterion acceptCriterion) {

		final BeanItemContainer<Question> tableContainer = new BeanItemContainer<Question>(
				Question.class);

		List<Question> questions = presenter.getQuestions();

		for (Question question : questions) {
			tableContainer.addItem(question);
		}

		table.setContainerDataSource(tableContainer);
		table.setVisibleColumns(new Object[] { "domain", "id" });

		// Handle drop in table: move Question item or subtree to the table
		table.setDragMode(TableDragMode.ROW);

		table.setDropHandler(new DropHandler() {
			private static final long serialVersionUID = 2024373906863943116L;

			public AcceptCriterion getAcceptCriterion() {
				return new And(acceptCriterion, AcceptItem.ALL);
			}

			public void drop(DragAndDropEvent event) {
				// Wrapper for the object that is dragged
				DataBoundTransferable t = (DataBoundTransferable) event
						.getTransferable();

				// Make sure the drag source is the same tree
				if (t.getSourceComponent() != tree
						&& t.getSourceComponent() != table)
					return;

				AbstractSelectTargetDetails target = (AbstractSelectTargetDetails) event
						.getTargetDetails();

				// Get ids of the dragged item and the target item
				Object sourceItemId = t.getData("itemId");
				Object targetItemId = target.getItemIdOver();
				
				// Do not allow drop on the item itself
				if (sourceItemId.equals(targetItemId))
					return;
			
				//get the question, if the item is a domain, exit.
				Question bean = null;
				if (sourceItemId instanceof BeanItem<?>)
					bean = (Question) ((BeanItem<?>) sourceItemId).getBean();
				else if (sourceItemId instanceof Question)
					bean = (Question) sourceItemId;
				else
					return;

				// On which side of the target the item was dropped
				VerticalDropLocation location = target.getDropLocation();

				// The table was empty or otherwise not on an item
				if (targetItemId == null)
					tableContainer.addItem(bean); // Add to the end

				// Drop at the top of a subtree -> make it previous
				else if (location == VerticalDropLocation.TOP)
					tableContainer.addItemAt(
							tableContainer.indexOfId(targetItemId), bean);

				// Drop below another item -> make it next
				else if (location == VerticalDropLocation.BOTTOM)
					tableContainer.addItemAfter(targetItemId, bean);

				// Remove the item from the source container
				t.getSourceContainer().removeItem(sourceItemId);
			}
		});
	}

	private static String getTreeNodeName(Container.Hierarchical source,
			Object sourceId) {
		return (String) source.getItem(sourceId)
				.getItemProperty(QuestionaireBuilderPresenter.NAME).getValue();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// the view is constructed in the init() method()
	}

	@Override
	public void fireViewEvent(ViewEvent ev) {
		// TODO

	}
}