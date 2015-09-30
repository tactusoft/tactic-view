package com.vaadin.demo.dashboard.view.catalog;

import java.util.List;

import org.netbeans.rest.application.config.SpringContextHelper;
import org.vaadin.maddon.ListContainer;

import com.tacticlogistics.model.bo.CatalogoBO;
import com.tacticlogistics.model.entities.Ciudades;
import com.tacticlogistics.model.entities.Departamentos;
import com.tacticlogistics.model.entities.Paises;
import com.tacticlogistics.model.entities.TiposDireccion;
import com.tacticlogistics.model.entities.TiposIdentificacion;
import com.tacticlogistics.model.entities.Zonas;
import com.vaadin.data.Container;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.VerticalLayout;

public class BaseClass extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	protected CatalogoBO catalogoBO;

	public BaseClass() {
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		catalogoBO = (CatalogoBO) helper.getBean("catalogoBO");
	}

	protected Container getContainerTiposIdentificacion() {
		List<TiposIdentificacion> list = catalogoBO.getListTiposIdentificacionActivo();
		Container container = new ListContainer<TiposIdentificacion>(TiposIdentificacion.class, list);
		return container;
	}

	protected Container getContainerTiposDireccion() {
		List<TiposDireccion> list = catalogoBO.getListTiposDireccionActivo();
		Container container = new ListContainer<TiposDireccion>(TiposDireccion.class, list);
		return container;
	}

	protected Container getContainerPaises() {
		List<Paises> list = catalogoBO.getListPaisesActivo();
		Container container = new ListContainer<Paises>(Paises.class, list);
		return container;
	}

	protected Container getContainerDepartamentos(Short paisId) {
		List<Departamentos> list = catalogoBO.getListDepartamentosActivoPorPais(paisId);
		Container container = new ListContainer<Departamentos>(Departamentos.class, list);
		return container;
	}

	protected Container getContainerCiudades(Short departamentoId) {
		List<Ciudades> list = catalogoBO.getListCiudadesActivoPorDepartamentos(departamentoId);
		Container container = new ListContainer<Ciudades>(Ciudades.class, list);
		return container;
	}

	protected Container getContainerZonas() {
		List<Zonas> list = catalogoBO.getListZonasActivo();
		Container container = new ListContainer<Zonas>(Zonas.class, list);
		return container;
	}

}
