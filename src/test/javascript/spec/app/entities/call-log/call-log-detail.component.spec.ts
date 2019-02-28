/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CallLogDetailComponent } from 'app/entities/call-log/call-log-detail.component';
import { CallLog } from 'app/shared/model/call-log.model';

describe('Component Tests', () => {
    describe('CallLog Management Detail Component', () => {
        let comp: CallLogDetailComponent;
        let fixture: ComponentFixture<CallLogDetailComponent>;
        const route = ({ data: of({ callLog: new CallLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CallLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CallLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.callLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
