import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import { ItemsAddEditComponent } from './items-add-edit.component';
import { FilesComponent } from '../files/files.component';
import { ItemsService } from '../../services/items.service';
import { Items } from '../../models/Items';
import { GroupsService } from '../../services/groups.service';
import { Groups } from '../../models/Groups';
import { FilesService } from '../../services/files.service';

describe('Component: ItemsAddEditComponent', () => {
	
	let activatedRouteValues: any;
	let routerMock: any;
	let routerSpy: any;
	let mockGroups: Array<Groups>;
	let mockItem: Items;
	let component: ItemsAddEditComponent;
	let fixture: ComponentFixture<ItemsAddEditComponent>;
	let itemsService: ItemsService;
	let groupsService: GroupsService;
	
	beforeEach(async(() => {
		routerMock = {navigate: jasmine.createSpy('navigate')};
		mockGroups = [{id: '1', name: 'Test Group 1'}, {id: '2', name: 'Test Group 2'}];
		mockItem = {id: '1', name: 'Item', groupId: '1'};
		activatedRouteValues = {snapshot: {params: {'id': '1'}}, url: ['items']};

		TestBed.configureTestingModule({
			imports: [ ReactiveFormsModule, FormsModule, HttpClientModule ],
			declarations: [ ItemsAddEditComponent, FilesComponent ],
			providers: [
				ItemsService,
				GroupsService,
				FilesService,
				{ provide: ActivatedRoute, useValue: activatedRouteValues},
				{ provide: Router, useValue: routerMock }
			]
		})
		.overrideComponent(FilesComponent, {
			set: {
				selector: 'app-files',
				template: `<input type="file" id="multipartFile"/>`
			}
		});

		fixture = TestBed.createComponent(ItemsAddEditComponent);
		component = fixture.componentInstance;
		itemsService = TestBed.get(ItemsService);
		groupsService = TestBed.get(GroupsService);
		routerSpy = TestBed.get(Router);

		spyOn(itemsService, 'getById')
			.and.returnValues(Observable.of(mockItem));
		spyOn(itemsService, 'save')
			.and.returnValues(Observable.of(mockItem));
		spyOn(groupsService, 'get')
			.and.returnValues(Observable.of(mockGroups));
	}));

	it('should create component', () => {
		expect(component).toBeTruthy();
	});

	it('should get groups when initialized', () => {
		fixture.whenStable().then(() => { 
			expect(component.groups).toEqual(mockGroups);
		});
		component.ngOnInit();
	});

	it('form name invalid when empty', () => {
		let form = component.itemForm;
		expect(form.valid).toBeFalsy();
		expect(form.get('name').valid).toEqual(false);
		form.get('name').setValue(mockItem.name);
		expect(form.get('name').valid).toEqual(true);
	});

	it('form groupId invalid when empty', () => {
		let form = component.itemForm;
		expect(form.valid).toBeFalsy();
		expect(form.get('groupId').valid).toEqual(false);
		form.get('groupId').setValue(mockItem.groupId);
		expect(form.get('groupId').valid).toEqual(true);
	});

	it('submit form', () => {
		const saveItem: Items = {name: mockItem.name, groupId: mockItem.groupId};
		let form = component.itemForm;
		expect(form.valid).toBeFalsy();
		form.get('name').setValue(mockItem.name);
		form.get('groupId').setValue(mockItem.groupId);
		expect(form.valid).toEqual(true);

		component.save(saveItem);
		expect(itemsService.save).toHaveBeenCalled();
		expect(itemsService.save).toHaveBeenCalledWith(saveItem);
		expect(routerSpy.navigate).toHaveBeenCalledWith(['/items']);
	});

});
