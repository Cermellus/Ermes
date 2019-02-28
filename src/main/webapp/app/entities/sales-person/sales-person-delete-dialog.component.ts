import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { SalesPersonService } from './sales-person.service';

@Component({
    selector: 'jhi-sales-person-delete-dialog',
    templateUrl: './sales-person-delete-dialog.component.html'
})
export class SalesPersonDeleteDialogComponent {
    salesPerson: ISalesPerson;

    constructor(
        protected salesPersonService: SalesPersonService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salesPersonService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'salesPersonListModification',
                content: 'Deleted an salesPerson'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sales-person-delete-popup',
    template: ''
})
export class SalesPersonDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salesPerson }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SalesPersonDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.salesPerson = salesPerson;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sales-person', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sales-person', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
