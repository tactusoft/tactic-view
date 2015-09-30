package com.vaadin.demo.dashboard.view.catalog;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tacticlogistics.model.entities.ClientesFinales;
import com.tacticlogistics.model.entities.Departamentos;
import com.tacticlogistics.model.entities.Direcciones;
import com.tacticlogistics.model.entities.Paises;
import com.tacticlogistics.model.entities.TiposDireccion;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ClientesView extends BaseClass implements View {

	private static final long serialVersionUID = 1L;

	private ClientesFinales clientesFinales;
	private Direcciones direcciones;
	private List<Direcciones> listDirecciones;

	private TabSheet tabs;

	public ClientesView() {
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
		tiposIdentificacion.setContainerDataSource(getContainerTiposIdentificacion());
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
				final Window win = getWinDirecciones();
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
		form.setMargin(true);
		form.setWidth("100%");

		ComboBox paises = new ComboBox("Pais");
		ComboBox departamentos = new ComboBox("Departamento");
		ComboBox ciudades = new ComboBox("Ciudades");

		paises.setItemCaptionPropertyId("nombre");
		paises.setRequired(true);
		paises.setTextInputAllowed(false);
		paises.setNullSelectionAllowed(true);
		paises.setWidth("100%");
		paises.setContainerDataSource(getContainerPaises());
		paises.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				Paises selectedValue = (Paises) event.getProperty().getValue();
				departamentos.setContainerDataSource(getContainerDepartamentos(selectedValue.getPaisId()));
			}
		});
		form.addComponent(paises);

		departamentos.setItemCaptionPropertyId("nombre");
		departamentos.setRequired(true);
		departamentos.setTextInputAllowed(true);
		departamentos.setNullSelectionAllowed(false);
		departamentos.setWidth("100%");
		departamentos.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				Departamentos selectedValue = (Departamentos) event.getProperty().getValue();
				ciudades.setContainerDataSource(getContainerCiudades(selectedValue.getId().getDepartamentoId()));
			}
		});
		form.addComponent(departamentos);

		ciudades.setItemCaptionPropertyId("nombre");
		ciudades.setRequired(true);
		ciudades.setTextInputAllowed(true);
		ciudades.setNullSelectionAllowed(false);
		ciudades.setWidth("100%");
		form.addComponent(ciudades);

		ComboBox tiposDireccion = new ComboBox("Tipo de Dirección");
		tiposDireccion.setItemCaptionPropertyId("nombre");
		tiposDireccion.setRequired(true);
		tiposDireccion.setTextInputAllowed(false);
		tiposDireccion.setNullSelectionAllowed(false);
		tiposDireccion.setWidth("100%");
		tiposDireccion.setContainerDataSource(getContainerTiposDireccion());
		form.addComponent(tiposDireccion);

		ComboBox zonas = new ComboBox("Zonas");
		zonas.setItemCaptionPropertyId("nombre");
		//zonas.setRequired(true);
		zonas.setTextInputAllowed(false);
		zonas.setNullSelectionAllowed(false);
		zonas.setWidth("100%");
		zonas.setContainerDataSource(getContainerZonas());
		form.addComponent(zonas);

		TextField direccion = new TextField("Dirección");
		direccion.setRequired(true);
		direccion.setValue("");
		direccion.setWidth("100%");
		form.addComponent(direccion);
		
		TextField indicaciones = new TextField("Indicaciones");
		indicaciones.setRequired(true);
		indicaciones.setValue("");
		indicaciones.setWidth("100%");
		form.addComponent(indicaciones);

		TextField barrio = new TextField("Barrio");
		barrio.setRequired(true);
		barrio.setValue("");
		barrio.setWidth("100%");
		form.addComponent(barrio);

		TextField codigoPostal = new TextField("Código Postal");
		//codigoPostal.setRequired(true);
		codigoPostal.setValue("");
		codigoPostal.setWidth("100%");
		form.addComponent(codigoPostal);

		TextField nombreContacto = new TextField("Nombre del Contacto");
		//nombreContacto.setRequired(true);
		nombreContacto.setValue("");
		nombreContacto.setWidth("100%");
		form.addComponent(nombreContacto);
		
		TextField cargoContacto = new TextField("Cargo del Contacto");
		//cargoContacto.setRequired(true);
		cargoContacto.setValue("");
		cargoContacto.setWidth("100%");
		form.addComponent(cargoContacto);
		
		TextField telefonoFijo = new TextField("Teléfono Fijo");
		telefonoFijo.setRequired(true);
		telefonoFijo.setValue("");
		telefonoFijo.setWidth("100%");
		form.addComponent(telefonoFijo);
		
		TextField telefonoCelular = new TextField("Teléfono Celular");
		telefonoCelular.setRequired(true);
		telefonoCelular.setValue("");
		telefonoCelular.setWidth("100%");
		form.addComponent(telefonoCelular);

		/*
		 * if (listDirecciones == null || listDirecciones.isEmpty()) {
		 * listDirecciones = new LinkedList<>(); TiposDireccion result = null;
		 * for (Iterator<?> it = zonas.getItemIds().iterator(); it.hasNext();) {
		 * TiposDireccion obj = (TiposDireccion) it.next(); if
		 * (obj.isPrincipal()) { result = obj; break; } } zonas.select(result);
		 * } else {
		 * 
		 * }
		 */
		return form;
	}

	Window getWinDirecciones() {
		final Window win = new Window("Dirección");
		win.setWidth("500px");
		win.setHeight("500px");
		win.setClosable(true);
		win.setResizable(false);

		VerticalLayout root = new VerticalLayout();
		root.setMargin(true);
		root.addComponent(getFormDireccion());

		HorizontalLayout footer = new HorizontalLayout();
		footer.setWidth("100%");
		footer.setSpacing(true);
		footer.addStyleName("v-window-bottom-toolbar");

		Label footerText = new Label();
		footerText.setSizeUndefined();

		Button ok = new Button("Aceptar");
		ok.addStyleName("primary");

		Button cancel = new Button("Cancelar");

		footer.addComponents(footerText, ok, cancel);
		footer.setExpandRatio(footerText, 1);
		root.addComponent(footer);

		win.setContent(root);
		win.setCloseShortcut(KeyCode.ESCAPE, null);
		win.setModal(true);
		return win;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
