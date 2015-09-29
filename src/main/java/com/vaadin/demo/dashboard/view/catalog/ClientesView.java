package com.vaadin.demo.dashboard.view.catalog;

import java.util.List;

import org.netbeans.rest.application.config.SpringContextHelper;
import org.vaadin.maddon.ListContainer;

import com.tacticlogistics.model.bo.CatalogoBO;
import com.tacticlogistics.model.entities.ClientesFinales;
import com.tacticlogistics.model.entities.Direcciones;
import com.tacticlogistics.model.entities.TiposIdentificacion;
import com.vaadin.data.Container;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ClientesView extends VerticalLayout implements View {

	private CatalogoBO catalogoBO;
	private ClientesFinales clientesFinales;
	private Direcciones direcciones;
	private List<Direcciones> direccionesList;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TabSheet tabs;

	public ClientesView() {
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		catalogoBO = (CatalogoBO) helper.getBean("catalogoBO");

		setMargin(true);

		Label h1 = new Label("Clientes");
		h1.addStyleName("h1");
		addComponent(h1);

		tabs = getTabSheet();
		addComponent(tabs);
	}

	TabSheet getTabSheet() {
		String style = "framed padded-tabbar compact-tabbar icons-on-top";
		TabSheet ts = new TabSheet();
		ts.addStyleName(style);

		String tabcaption = "Consulta";
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		content.addComponent(new Label("Content for tab " + 2));
		Tab consultaTab = ts.addTab(content, tabcaption);
		consultaTab.setClosable(false);

		tabcaption = "Nuevo y Edición";
		content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		content.addComponent(getFormInformacionPersonal());
		Tab nuevoTab = ts.addTab(content, tabcaption);
		nuevoTab.setClosable(false);

		ts.setSelectedTab(nuevoTab);

		return ts;
	}

	FormLayout getFormInformacionPersonal() {
		final FormLayout form = new FormLayout();
		form.setMargin(false);
		form.setWidth("800px");

		Label section = new Label("Información Personal");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		ComboBox tiposIdentificacion = new ComboBox("Tipo de Identificación");
		tiposIdentificacion.setItemCaptionPropertyId("nombre");
		tiposIdentificacion.setRequired(true);
		tiposIdentificacion.setTextInputAllowed(false);
		tiposIdentificacion.setNullSelectionAllowed(false);
		tiposIdentificacion.setWidth("70%");
		tiposIdentificacion.setContainerDataSource(getTiposIdentificacion());
		form.addComponent(tiposIdentificacion);

		TextField identificacion = new TextField("Identificación");
		identificacion.setRequired(true);
		identificacion.setValue("");
		identificacion.setWidth("70%");
		form.addComponent(identificacion);

		TextField razonSocial = new TextField("Razón Social");
		razonSocial.setRequired(true);
		razonSocial.setValue("");
		razonSocial.setWidth("70%");
		form.addComponent(razonSocial);

		TextField nombreComercial = new TextField("Nombre Comercial");
		nombreComercial.setRequired(true);
		nombreComercial.setValue("");
		nombreComercial.setWidth("70%");
		form.addComponent(nombreComercial);

		section = new Label("Direcciones");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		Button nuevaDireccion = new Button("Nueva Dirección");
		nuevaDireccion.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				final Window win = new Window("Nueva Dirección");
				win.setWidth("380px");
				win.setHeight(null);
				win.setClosable(true);
				win.setResizable(false);
				// win.setContent(windowContent());
				win.setCloseShortcut(KeyCode.ESCAPE, null);
				win.setModal(true);
				getUI().addWindow(win);
				win.center();
				win.focus();
			}
		});
		form.addComponent(nuevaDireccion);

		return form;
	}

	FormLayout getFormDireccion() {
		final FormLayout form = new FormLayout();
		form.setMargin(false);
		form.setWidth("800px");

		Label section = new Label("Información Personal");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		ComboBox tiposIdentificacion = new ComboBox("Tipo de Identificación");
		tiposIdentificacion.setItemCaptionPropertyId("nombre");
		tiposIdentificacion.setRequired(true);
		tiposIdentificacion.setTextInputAllowed(false);
		tiposIdentificacion.setNullSelectionAllowed(false);
		tiposIdentificacion.setWidth("70%");
		tiposIdentificacion.setContainerDataSource(getTiposIdentificacion());
		form.addComponent(tiposIdentificacion);

		TextField identificacion = new TextField("Identificación");
		identificacion.setRequired(true);
		identificacion.setValue("");
		identificacion.setWidth("70%");
		form.addComponent(identificacion);

		TextField razonSocial = new TextField("Razón Social");
		razonSocial.setRequired(true);
		razonSocial.setValue("");
		razonSocial.setWidth("70%");
		form.addComponent(razonSocial);

		TextField nombreComercial = new TextField("Nombre Comercial");
		nombreComercial.setRequired(true);
		nombreComercial.setValue("");
		nombreComercial.setWidth("70%");
		form.addComponent(nombreComercial);

		section = new Label("Direcciones");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		Button nuevaDireccion = new Button("Nueva Dirección");
		nuevaDireccion.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				final Window win = new Window("Nueva Dirección");
				win.setWidth("380px");
				win.setHeight(null);
				win.setClosable(true);
				win.setResizable(false);
				// win.setContent(windowContent());
				win.setCloseShortcut(KeyCode.ESCAPE, null);
				win.setModal(true);
				getUI().addWindow(win);
				win.center();
				win.focus();
			}
		});
		form.addComponent(nuevaDireccion);

		return form;
	}

	Container getTiposIdentificacion() {
		List<TiposIdentificacion> list = catalogoBO.getListTiposIdentificacionActivo();
		Container container = new ListContainer<TiposIdentificacion>(TiposIdentificacion.class, list);
		return container;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
