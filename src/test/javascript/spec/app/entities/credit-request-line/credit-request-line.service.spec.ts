/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CreditRequestLineService } from 'app/entities/credit-request-line/credit-request-line.service';
import { ICreditRequestLine, CreditRequestLine } from 'app/shared/model/credit-request-line.model';

describe('Service Tests', () => {
    describe('CreditRequestLine Service', () => {
        let injector: TestBed;
        let service: CreditRequestLineService;
        let httpMock: HttpTestingController;
        let elemDefault: ICreditRequestLine;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CreditRequestLineService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new CreditRequestLine(0, 0, 0, 0, 0, 'AAAAAAA', 'image/png', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CreditRequestLine', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new CreditRequestLine(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CreditRequestLine', async () => {
                const returnedFromService = Object.assign(
                    {
                        creditRequestLineId: 1,
                        creditRequestLineAmount: 1,
                        creditRequestLineQtyCredited: 1,
                        creditRequestLineQtyReturn: 1,
                        creditRequestLineComment: 'BBBBBB',
                        creditRequestLineImage: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CreditRequestLine', async () => {
                const returnedFromService = Object.assign(
                    {
                        creditRequestLineId: 1,
                        creditRequestLineAmount: 1,
                        creditRequestLineQtyCredited: 1,
                        creditRequestLineQtyReturn: 1,
                        creditRequestLineComment: 'BBBBBB',
                        creditRequestLineImage: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a CreditRequestLine', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
