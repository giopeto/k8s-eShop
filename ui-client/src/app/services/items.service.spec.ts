import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ItemsService } from './items.service';
import { STORE_SERVICE_BASE_URL, HTTP_OPTIONS } from '../common/ApiConstants';
import { Items } from '../models/Items';

describe('ItemsService', () => {
	let service: ItemsService;
    let httpMock: HttpTestingController;

	let apiUrl: string;
	let mockItems: Array<Items>;

	beforeEach(() => {
		apiUrl = `${STORE_SERVICE_BASE_URL}/items`;
		mockItems = [{id: '1', name: 'Item 1', groupId: '1'}, {id: '2', name: 'Item 2', groupId: '2'}];

		TestBed.configureTestingModule({
			imports: [ HttpClientTestingModule ],
			providers: [ ItemsService ]
		});

		service = TestBed.get(ItemsService);
		httpMock = TestBed.get(HttpTestingController);
	});

	afterEach(() => httpMock.verify());

	it('should be created', () => {
		expect(service).toBeTruthy();
	});

	it('expects service to fetch data', () => {
		service.get().subscribe(data => {
			expect(data.length).toBe(2);
			expect(data[0].name).toEqual(mockItems[0].name);
		});

		const req = httpMock.expectOne(apiUrl);
		expect(req.request.method).toEqual('GET');
		req.flush(mockItems);
	});

	it('expects service to fetch data by id', () => {
		const id = '1';
		service.getById(id).subscribe(data => {
			expect(data.name).toEqual(mockItems[0].name);
		});

		const req = httpMock.expectOne(`${apiUrl}/${id}`);
		expect(req.request.method).toEqual('GET');
		req.flush(mockItems[0]);
	});

	it('expects service to post data', () => {
		const newItemId = '3';
		const item: Items = {name: 'New item', groupId: '1'};
		service.save(item).subscribe(data => {
			expect(data.id).toEqual(newItemId);
			expect(data.name).toEqual(item.name);
		});

		const req = httpMock.expectOne(apiUrl);
		expect(req.request.method).toEqual('POST');
		req.flush({id: newItemId, ...item});
	});

	it('expects service to put data', () => {
		const itemId = '3';
		const item: Items = {id: itemId, name: 'New item 2', groupId: '1'};

		service.update(item).subscribe(data => {
			expect(data.id).toEqual(itemId);
			expect(data.name).toEqual(item.name);
		});

		const req = httpMock.expectOne(`${apiUrl}/${itemId}`);
		expect(req.request.method).toEqual('PUT');
		req.flush(item);
	});

	it('expects service to delete data', () => {
		service.delete(mockItems[0].id).subscribe();

		const req = httpMock.expectOne(`${apiUrl}/${mockItems[0].id}`);
		expect(req.request.method).toEqual('DELETE');
		req.flush(null);
	});
});