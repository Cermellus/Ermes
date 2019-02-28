/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestLineDetailComponent } from 'app/entities/credit-request-line/credit-request-line-detail.component';
import { CreditRequestLine } from 'app/shared/model/credit-request-line.model';

describe('Component Tests', () => {
    describe('CreditRequestLine Management Detail Component', () => {
        let comp: CreditRequestLineDetailComponent;
        let fixture: ComponentFixture<CreditRequestLineDetailComponent>;
        const route = ({ data: of({ creditRequestLine: new CreditRequestLine(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestLineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditRequestLineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditRequestLineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.creditRequestLine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
